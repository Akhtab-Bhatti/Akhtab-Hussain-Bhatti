package com.example.data.model

data class SceneItem(
    val id: String,
    val sceneNumber: Int,
    val timeRange: String,
    val durationSeconds: Int,
    val visualPrompt: String,
    val seed: String,
    val voiceoverVoice: String,
    val wordCount: Int,
    val voiceoverText: String,
    val captionsText: String,
    val imageUrl: String,
    val isActive: Boolean = false,
    val isReady: Boolean = true
)
