package com.example.data.repository

import android.util.Log
import com.example.BuildConfig
import com.example.data.Constants
import com.example.data.api.GenerateVideosRequest
import com.example.data.api.VeoApiClient
import com.example.data.api.VeoConfig
import com.example.data.model.AspectRatioOption
import com.example.data.model.ProjectItem
import com.example.data.model.ProjectStatus
import com.example.data.model.SceneItem
import com.example.data.model.StudioConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class VideoRepository {

    private val _projects = MutableStateFlow(
        listOf(
            ProjectItem(
                id = "p1",
                title = "Introduction to Neural Networks",
                durationSeconds = 60,
                resolution = "1080P FHD",
                aspectRatio = "9:16",
                status = ProjectStatus.COMPLETED,
                timeAgo = "2h ago",
                imageUrl = Constants.THUMB_NEURAL_NETWORKS
            ),
            ProjectItem(
                id = "p2",
                title = "Quarterly FinTech Launch Reel",
                durationSeconds = 45,
                resolution = "4K ULTRA",
                aspectRatio = "16:9",
                status = ProjectStatus.READY,
                timeAgo = "Yesterday",
                views = "1.4k views",
                imageUrl = Constants.THUMB_FINTECH
            ),
            ProjectItem(
                id = "p3",
                title = "Daily Micro-Habits for Productivity",
                durationSeconds = 30,
                resolution = "1080P",
                aspectRatio = "1:1",
                status = ProjectStatus.RENDERING,
                timeAgo = "Just now",
                imageUrl = Constants.THUMB_HABITS,
                progressPercent = 80,
                activeSceneText = "Scene 4/5",
                subtitleStatus = "Synthesizing voiceover..."
            ),
            ProjectItem(
                id = "p4",
                title = "History of Ancient Rome in 3 Min",
                durationSeconds = 180,
                resolution = "1080P",
                aspectRatio = "16:9",
                status = ProjectStatus.ARCHIVED,
                timeAgo = "3d ago",
                imageUrl = Constants.THUMB_ROME
            )
        )
    )
    val projects: StateFlow<List<ProjectItem>> = _projects.asStateFlow()

    private val _scenes = MutableStateFlow(
        listOf(
            SceneItem(
                id = "s1",
                sceneNumber = 1,
                timeRange = "00:00 - 00:10 (10s)",
                durationSeconds = 10,
                visualPrompt = "Close-up shot of a glowing digital neural brain connecting glowing data pathways.",
                seed = "#92841",
                voiceoverVoice = "Nova - Warm",
                wordCount = 21,
                voiceoverText = "Imagine your brain making connections when you learn something new. That is exactly how Artificial Intelligence works.",
                captionsText = "\"AI learns just like your neural pathways.\"",
                imageUrl = Constants.SCENE_1_THUMB,
                isActive = true,
                isReady = true
            ),
            SceneItem(
                id = "s2",
                sceneNumber = 2,
                timeRange = "00:10 - 00:20 (10s)",
                durationSeconds = 10,
                visualPrompt = "High-tech classroom setting where students explore robotic and algorithmic concepts with curiosity.",
                seed = "#48123",
                voiceoverVoice = "Nova - Warm",
                wordCount = 17,
                voiceoverText = "Instead of memorizing lines of code, modern algorithms learn from millions of examples and patterns.",
                captionsText = "\"Learning from patterns, not just rules.\"",
                imageUrl = Constants.SCENE_2_THUMB,
                isActive = false,
                isReady = true
            ),
            SceneItem(
                id = "s3",
                sceneNumber = 3,
                timeRange = "00:20 - 00:32 (12s)",
                durationSeconds = 12,
                visualPrompt = "Split screen showing thousands of cat and dog pictures filtering through decision layers.",
                seed = "#10398",
                voiceoverVoice = "Nova - Warm",
                wordCount = 20,
                voiceoverText = "Show the computer ten thousand pictures of cats, and it figures out the ears, whiskers, and shapes by itself!",
                captionsText = "\"Recognizing features automatically from big data.\"",
                imageUrl = Constants.SCENE_3_THUMB,
                isActive = false,
                isReady = true
            )
        )
    )
    val scenes: StateFlow<List<SceneItem>> = _scenes.asStateFlow()

    fun updateSceneVoiceover(sceneId: String, newText: String) {
        _scenes.value = _scenes.value.map { scene ->
            if (scene.id == sceneId) scene.copy(voiceoverText = newText, wordCount = newText.split("\\s+".toRegex()).size)
            else scene
        }
    }

    fun removeScene(sceneId: String) {
        _scenes.value = _scenes.value.filterNot { it.id == sceneId }
    }

    fun addScene(newScene: SceneItem) {
        _scenes.value = _scenes.value + newScene
    }

    fun addProject(project: ProjectItem) {
        _projects.value = listOf(project) + _projects.value
    }

    fun updateProject(projectId: String, transform: (ProjectItem) -> ProjectItem) {
        _projects.value = _projects.value.map {
            if (it.id == projectId) transform(it) else it
        }
    }

    suspend fun generateVideoWithVeo(
        prompt: String,
        aspectRatioStr: String = "16:9",
        onProgressUpdate: (progress: Int, stageText: String, secondsRemaining: Int) -> Unit
    ): Result<String?> {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            Log.w("VideoRepository", "Gemini API key is not set or using placeholder.")
            return Result.failure(IllegalStateException("API key missing. Please set GEMINI_API_KEY in the Secrets panel."))
        }

        return try {
            onProgressUpdate(15, "Submitting video prompt to Veo 3.1 Fast...", 35)

            // Convert aspect ratio format if needed (e.g. "16:9", "9:16", "1:1")
            val validAspectRatio = when (aspectRatioStr) {
                "9:16" -> "9:16"
                "1:1" -> "1:1"
                else -> "16:9"
            }

            val request = GenerateVideosRequest(
                prompt = prompt,
                config = VeoConfig(
                    numberOfVideos = 1,
                    resolution = "1080p",
                    aspectRatio = validAspectRatio
                )
            )

            val initialResponse = VeoApiClient.service.generateVideos(
                model = "veo-3.1-fast-generate-preview",
                apiKey = apiKey,
                request = request
            )

            if (initialResponse.error != null) {
                return Result.failure(Exception(initialResponse.error.message ?: "Failed to initiate video generation"))
            }

            val operationName = initialResponse.name
            if (operationName.isNullOrEmpty()) {
                // If it returned video directly in response
                val directUri = initialResponse.response?.generateVideoResponse?.generatedSamples?.firstOrNull()?.video?.uri
                return Result.success(directUri)
            }

            // Poll for completion
            var isDone = initialResponse.done ?: false
            var pollCount = 0
            val maxPolls = 30 // up to ~60-90 seconds
            var videoUri: String? = null

            while (!isDone && pollCount < maxPolls) {
                delay(3000)
                pollCount++
                val calculatedProgress = 20 + (pollCount * 2).coerceAtMost(75)
                val remainingSec = (30 - pollCount * 2).coerceAtLeast(5)
                onProgressUpdate(
                    calculatedProgress,
                    "Veo AI rendering neural video frames (Pass $pollCount)...",
                    remainingSec
                )

                val pollResponse = VeoApiClient.service.getOperation(
                    operationName = operationName,
                    apiKey = apiKey
                )

                if (pollResponse.error != null) {
                    return Result.failure(Exception(pollResponse.error.message ?: "Error during video rendering"))
                }

                if (pollResponse.done == true) {
                    isDone = true
                    videoUri = pollResponse.response?.generateVideoResponse?.generatedSamples?.firstOrNull()?.video?.uri
                    break
                }
            }

            onProgressUpdate(95, "Finalizing audio synchronization & color grading...", 2)
            delay(1000)
            Result.success(videoUri)
        } catch (e: Exception) {
            Log.e("VideoRepository", "Error in Veo video generation", e)
            Result.failure(e)
        }
    }
}
