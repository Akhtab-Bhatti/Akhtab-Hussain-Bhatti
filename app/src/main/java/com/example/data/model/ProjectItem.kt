package com.example.data.model

data class ProjectItem(
    val id: String,
    val title: String,
    val durationSeconds: Int,
    val resolution: String,
    val aspectRatio: String,
    val status: ProjectStatus,
    val timeAgo: String,
    val views: String? = null,
    val imageUrl: String,
    val progressPercent: Int = 100,
    val activeSceneText: String? = null,
    val subtitleStatus: String? = null,
    val videoUri: String? = null
)

enum class ProjectStatus {
    COMPLETED,
    READY,
    RENDERING,
    ARCHIVED,
    DRAFT
}
