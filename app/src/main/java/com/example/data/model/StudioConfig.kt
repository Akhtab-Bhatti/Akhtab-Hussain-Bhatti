package com.example.data.model

data class StudioConfig(
    val prompt: String = "Create a 60-second educational video explaining Artificial Intelligence to Grade 10 students with vivid metaphors.",
    val durationOption: DurationOption = DurationOption.OPTIMAL_60S,
    val aspectRatio: AspectRatioOption = AspectRatioOption.REELS_9_16,
    val visualStyle: VisualStyleOption = VisualStyleOption.EDUCATIONAL,
    val voicePersona: VoicePersonaOption = VoicePersonaOption.ARIA,
    val language: String = "English (US)",
    val backgroundScore: String = "Lo-Fi Study Beat (Chill Synth)",
    val backgroundScoreDuck: String = "Duck -12dB"
)

enum class DurationOption(val label: String, val subLabel: String, val seconds: Int) {
    SHORT_30S("30s", "Short", 30),
    OPTIMAL_60S("60s", "Optimal", 60),
    DEEP_DIVE_2M("2m", "Deep Dive", 120),
    LONG_FORM_5M("5m", "Long Form", 300)
}

enum class AspectRatioOption(val ratio: String, val label: String, val iconName: String) {
    LANDSCAPE_16_9("16:9", "Landscape", "tv"),
    REELS_9_16("9:16", "Reels / Shorts", "smartphone"),
    SQUARE_1_1("1:1", "Square Post", "crop_square")
}

enum class VisualStyleOption(
    val title: String,
    val description: String,
    val imageUrl: String
) {
    EDUCATIONAL(
        title = "Educational",
        description = "Diagrams, motion callouts & graphs",
        imageUrl = com.example.data.Constants.STYLE_EDUCATIONAL
    ),
    CINEMATIC(
        title = "Cinematic",
        description = "Film grain, dynamic lighting",
        imageUrl = com.example.data.Constants.STYLE_CINEMATIC
    ),
    CORPORATE(
        title = "Corporate",
        description = "Clean, professional & polished",
        imageUrl = com.example.data.Constants.STYLE_CORPORATE
    ),
    SOCIAL_MEDIA(
        title = "Social Media",
        description = "High retention hooks & pop",
        imageUrl = com.example.data.Constants.STYLE_SOCIAL_MEDIA
    ),
    EXPLAINER(
        title = "Explainer",
        description = "Vector isometric graphics",
        imageUrl = com.example.data.Constants.STYLE_EXPLAINER
    ),
    CARTOON(
        title = "Cartoon",
        description = "Expressive 2D animated cel",
        imageUrl = com.example.data.Constants.STYLE_CARTOON
    )
}

enum class VoicePersonaOption(val nameTag: String, val gender: String, val description: String) {
    ARIA("Aria", "Female", "Warm & Engaging"),
    MARCUS("Marcus", "Male", "Deep & Authoritative")
}
