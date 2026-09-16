package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Constants
import com.example.data.model.AspectRatioOption
import com.example.data.model.DurationOption
import com.example.data.model.ProjectItem
import com.example.data.model.ProjectStatus
import com.example.data.model.SceneItem
import com.example.data.model.StudioConfig
import com.example.data.model.VisualStyleOption
import com.example.data.model.VoicePersonaOption
import com.example.data.repository.VideoRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppNavigationTab(val title: String, val iconName: String) {
    HOME("Home", "explore"),
    STUDIO("Studio", "videocam"),
    STORYBOARD("Projects", "view_kanban"),
    TIMELINE("Timeline", "movie_edit"),
    EXPORTS("Exports", "download_for_offline")
}

data class GenerationState(
    val isGenerating: Boolean = false,
    val progressPercent: Int = 68,
    val estimatedSecondsRemaining: Int = 18,
    val currentStageIndex: Int = 3, // 1 to 6
    val currentStageText: String = "Generating AI Scene Visuals",
    val isComplete: Boolean = false,
    val videoUri: String? = null,
    val errorMessage: String? = null
)

class VideoStudioViewModel(
    private val repository: VideoRepository = VideoRepository()
) : ViewModel() {

    // Current Navigation Tab
    private val _currentTab = MutableStateFlow(AppNavigationTab.HOME)
    val currentTab: StateFlow<AppNavigationTab> = _currentTab.asStateFlow()

    // Active subview overlay for Generation
    private val _isShowingGenerationScreen = MutableStateFlow(false)
    val isShowingGenerationScreen: StateFlow<Boolean> = _isShowingGenerationScreen.asStateFlow()

    // Home Screen view mode (Dashboard vs Showcase)
    private val _homeViewMode = MutableStateFlow("dashboard") // "dashboard" or "showcase"
    val homeViewMode: StateFlow<String> = _homeViewMode.asStateFlow()

    // Studio Configuration
    private val _studioConfig = MutableStateFlow(StudioConfig())
    val studioConfig: StateFlow<StudioConfig> = _studioConfig.asStateFlow()

    // Active Generation State
    private val _generationState = MutableStateFlow(GenerationState())
    val generationState: StateFlow<GenerationState> = _generationState.asStateFlow()

    // Search and Filters for Projects
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _activeFilter = MutableStateFlow("All Videos")
    val activeFilter: StateFlow<String> = _activeFilter.asStateFlow()

    // Storyboard Scenes
    val scenes: StateFlow<List<SceneItem>> = repository.scenes

    // Filtered Projects
    val filteredProjects: StateFlow<List<ProjectItem>> = combine(
        repository.projects,
        _searchQuery,
        _activeFilter
    ) { projects, query, filter ->
        projects.filter { project ->
            val matchesQuery = query.isEmpty() ||
                    project.title.contains(query, ignoreCase = true)
            val matchesFilter = when (filter) {
                "All Videos" -> true
                "Completed" -> project.status == ProjectStatus.COMPLETED
                "Drafts" -> project.status == ProjectStatus.DRAFT
                "In Progress" -> project.status == ProjectStatus.RENDERING
                "9:16 Shorts" -> project.aspectRatio == "9:16"
                "16:9 Landscape" -> project.aspectRatio == "16:9"
                else -> true
            }
            matchesQuery && matchesFilter
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // Timeline Editor State
    private val _selectedTimelineScene = MutableStateFlow(2) // Scene 2 selected by default
    val selectedTimelineScene: StateFlow<Int> = _selectedTimelineScene.asStateFlow()

    private val _isTimelinePlaying = MutableStateFlow(false)
    val isTimelinePlaying: StateFlow<Boolean> = _isTimelinePlaying.asStateFlow()

    private val _timelinePositionSeconds = MutableStateFlow(14.20f)
    val timelinePositionSeconds: StateFlow<Float> = _timelinePositionSeconds.asStateFlow()

    // Audio Preview State in Studio / Storyboard
    private val _isAudioPreviewing = MutableStateFlow(false)
    val isAudioPreviewing: StateFlow<Boolean> = _isAudioPreviewing.asStateFlow()

    // Notification / Toast Message
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    private var generationJob: Job? = null

    fun selectTab(tab: AppNavigationTab) {
        _currentTab.value = tab
        _isShowingGenerationScreen.value = false
    }

    fun setHomeViewMode(mode: String) {
        _homeViewMode.value = mode
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setActiveFilter(filter: String) {
        _activeFilter.value = filter
    }

    fun setPrompt(prompt: String) {
        _studioConfig.value = _studioConfig.value.copy(prompt = prompt)
    }

    fun setDuration(duration: DurationOption) {
        _studioConfig.value = _studioConfig.value.copy(durationOption = duration)
    }

    fun setAspectRatio(ratio: AspectRatioOption) {
        _studioConfig.value = _studioConfig.value.copy(aspectRatio = ratio)
    }

    fun setVisualStyle(style: VisualStyleOption) {
        _studioConfig.value = _studioConfig.value.copy(visualStyle = style)
    }

    fun setVoicePersona(persona: VoicePersonaOption) {
        _studioConfig.value = _studioConfig.value.copy(voicePersona = persona)
    }

    fun enhancePrompt() {
        _studioConfig.value = _studioConfig.value.copy(
            prompt = "Create an electrifying 60-second visual breakdown explaining Artificial Intelligence neural networks to high-school students, using dynamic metaphors of train networks and cosmic stars, punchy motion graphics, and ultra-crisp kinetic typography."
        )
        showToast("Prompt enhanced with cinematic visual cues!")
    }

    fun suggestTone() {
        val current = _studioConfig.value.prompt
        _studioConfig.value = _studioConfig.value.copy(
            prompt = "$current Tone: Playful yet authoritative, conversational pacing, captivating opening hook."
        )
        showToast("Tone suggestions appended!")
    }

    fun clearPrompt() {
        _studioConfig.value = _studioConfig.value.copy(prompt = "")
    }

    fun toggleAudioPreview() {
        _isAudioPreviewing.value = !_isAudioPreviewing.value
    }

    fun startGeneration(customPrompt: String? = null) {
        if (customPrompt != null) {
            _studioConfig.value = _studioConfig.value.copy(prompt = customPrompt)
        }
        val prompt = _studioConfig.value.prompt.ifBlank { "Cinematic futuristic cityscape at twilight with flying vehicles" }
        val aspectRatioStr = _studioConfig.value.aspectRatio.ratio

        _isShowingGenerationScreen.value = true
        _generationState.value = GenerationState(
            isGenerating = true,
            progressPercent = 10,
            estimatedSecondsRemaining = 40,
            currentStageIndex = 1,
            currentStageText = "Connecting to Veo 3.1 Neural Video Cluster",
            isComplete = false,
            videoUri = null,
            errorMessage = null
        )

        val newProjectId = "proj_${System.currentTimeMillis()}"
        val initialProject = ProjectItem(
            id = newProjectId,
            title = prompt.take(40) + if (prompt.length > 40) "..." else "",
            durationSeconds = _studioConfig.value.durationOption.seconds,
            resolution = "1080P FHD",
            aspectRatio = aspectRatioStr,
            status = ProjectStatus.RENDERING,
            timeAgo = "Just now",
            imageUrl = Constants.SCENE_1_THUMB,
            progressPercent = 10,
            activeSceneText = "Initializing Veo",
            subtitleStatus = "Connecting to Veo...",
            videoUri = null
        )
        repository.addProject(initialProject)

        generationJob?.cancel()
        generationJob = viewModelScope.launch {
            val result = repository.generateVideoWithVeo(
                prompt = prompt,
                aspectRatioStr = aspectRatioStr
            ) { progress, stageText, secondsRemaining ->
                val stageIndex = when {
                    progress < 25 -> 1
                    progress < 50 -> 2
                    progress < 75 -> 3
                    progress < 90 -> 4
                    progress < 98 -> 5
                    else -> 6
                }
                _generationState.value = _generationState.value.copy(
                    progressPercent = progress,
                    estimatedSecondsRemaining = secondsRemaining,
                    currentStageIndex = stageIndex,
                    currentStageText = stageText
                )
                repository.updateProject(newProjectId) { p ->
                    p.copy(
                        progressPercent = progress,
                        subtitleStatus = stageText,
                        activeSceneText = "Stage $stageIndex/6"
                    )
                }
            }

            result.fold(
                onSuccess = { videoUri ->
                    _generationState.value = _generationState.value.copy(
                        progressPercent = 100,
                        estimatedSecondsRemaining = 0,
                        currentStageIndex = 6,
                        currentStageText = "Masterpiece Ready! Generated via Veo 3.1",
                        isComplete = true,
                        isGenerating = false,
                        videoUri = videoUri
                    )
                    repository.updateProject(newProjectId) { p ->
                        p.copy(
                            status = ProjectStatus.COMPLETED,
                            progressPercent = 100,
                            subtitleStatus = "Veo 3.1 Render Complete",
                            activeSceneText = "Ready",
                            videoUri = videoUri
                        )
                    }
                    showToast("Video generated successfully!")
                },
                onFailure = { error ->
                    val errorMsg = error.message ?: "Failed to generate video."
                    // Check if it's missing API key
                    if (errorMsg.contains("API key missing", ignoreCase = true)) {
                        _generationState.value = _generationState.value.copy(
                            progressPercent = 100,
                            estimatedSecondsRemaining = 0,
                            currentStageIndex = 6,
                            currentStageText = "Veo AI Configured (Add GEMINI_API_KEY to test live)",
                            isComplete = true,
                            isGenerating = false,
                            errorMessage = "No Gemini API Key found in Secrets panel. Add your key to render live Veo videos!"
                        )
                        repository.updateProject(newProjectId) { p ->
                            p.copy(
                                status = ProjectStatus.COMPLETED,
                                progressPercent = 100,
                                subtitleStatus = "Preview Ready",
                                activeSceneText = "Ready"
                            )
                        }
                        showToast("Enter GEMINI_API_KEY in Secrets panel for live Veo rendering")
                    } else {
                        _generationState.value = _generationState.value.copy(
                            isGenerating = false,
                            isComplete = false,
                            currentStageText = "Generation failed",
                            errorMessage = errorMsg
                        )
                        repository.updateProject(newProjectId) { p ->
                            p.copy(
                                status = ProjectStatus.DRAFT,
                                subtitleStatus = "Generation failed: $errorMsg"
                            )
                        }
                        showToast("Veo Error: $errorMsg")
                    }
                }
            )
        }
    }

    fun dismissGenerationScreen() {
        _isShowingGenerationScreen.value = false
    }

    fun selectTimelineScene(sceneNumber: Int) {
        _selectedTimelineScene.value = sceneNumber
    }

    fun toggleTimelinePlay() {
        _isTimelinePlaying.value = !_isTimelinePlaying.value
    }

    fun updateSceneVoiceover(sceneId: String, text: String) {
        repository.updateSceneVoiceover(sceneId, text)
    }

    fun removeScene(sceneId: String) {
        repository.removeScene(sceneId)
        showToast("Scene removed")
    }

    fun addScene() {
        val currentCount = scenes.value.size
        val newScene = SceneItem(
            id = "s${System.currentTimeMillis()}",
            sceneNumber = currentCount + 1,
            timeRange = "00:${currentCount * 10} - 00:${(currentCount + 1) * 10} (10s)",
            durationSeconds = 10,
            visualPrompt = "Dynamic cinematic shot illustrating AI concepts with luminescent glowing nodes.",
            seed = "#${(10000..99999).random()}",
            voiceoverVoice = "Nova - Warm",
            wordCount = 18,
            voiceoverText = "As data passes through each node, probabilities adjust to deliver intelligent predictions.",
            captionsText = "\"Transforming raw inputs into contextual intelligence.\"",
            imageUrl = Constants.SCENE_1_THUMB,
            isActive = false,
            isReady = true
        )
        repository.addScene(newScene)
        showToast("New scene added!")
    }

    fun showToast(msg: String) {
        _toastMessage.value = msg
        viewModelScope.launch {
            delay(2500)
            if (_toastMessage.value == msg) {
                _toastMessage.value = null
            }
        }
    }

    fun clearToast() {
        _toastMessage.value = null
    }
}
