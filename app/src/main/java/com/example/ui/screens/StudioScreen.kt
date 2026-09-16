package com.example.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Crop
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.ModelTraining
import androidx.compose.material.icons.filled.MovieFilter
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.filled.Token
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.AspectRatioOption
import com.example.data.model.DurationOption
import com.example.data.model.VisualStyleOption
import com.example.data.model.VoicePersonaOption
import com.example.ui.components.TopBar
import com.example.ui.theme.HeroCtaGradient
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnSecondary
import com.example.ui.theme.OnSecondaryContainer
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.OnTertiary
import com.example.ui.theme.Outline
import com.example.ui.theme.Primary
import com.example.ui.theme.PrimaryContainer
import com.example.ui.theme.Secondary
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.Surface
import com.example.ui.theme.SurfaceBright
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.Tertiary
import com.example.ui.theme.TertiaryContainer
import com.example.ui.viewmodel.VideoStudioViewModel

@Composable
fun StudioScreen(
    viewModel: VideoStudioViewModel,
    onBackClick: () -> Unit,
    onStartGeneration: () -> Unit,
    modifier: Modifier = Modifier
) {
    val config by viewModel.studioConfig.collectAsState()
    val isAudioPreviewing by viewModel.isAudioPreviewing.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Surface)
            .testTag("studio_screen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar
            TopBar(
                title = "New Video Studio",
                showBackButton = true,
                onBackClick = onBackClick,
                showNotifications = true,
                showMore = false
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header with Step Indicator
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                PulsingDot(color = Tertiary)
                                Text(
                                    text = "AI SYNTHESIZER 3.5",
                                    color = Tertiary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SurfaceContainerHigh)
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text("Step 1 of 3", color = OnSurfaceVariant, fontSize = 11.sp)
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(Primary)
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 2.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                text = "New Video Studio",
                                color = OnSurface,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Setup",
                                color = OnSurfaceVariant,
                                fontSize = 12.sp
                            )
                        }

                        // Linear Step Bar
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(5.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(SurfaceContainerHighest)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.33f)
                                    .height(5.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(
                                        Brush.horizontalGradient(
                                            listOf(PrimaryContainer, Primary, Secondary)
                                        )
                                    )
                            )
                        }

                        Text(
                            text = "Concept & Parameters",
                            color = Primary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Creative Prompt Section
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Psychology,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "Creative Prompt",
                                    color = OnSurface,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Text(
                                text = "${config.prompt.length} / 500",
                                color = Primary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                OutlinedTextField(
                                    value = config.prompt,
                                    onValueChange = { if (it.length <= 500) viewModel.setPrompt(it) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("creative_prompt_input"),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedBorderColor = Color.Transparent,
                                        unfocusedBorderColor = Color.Transparent,
                                        focusedTextColor = OnSurface,
                                        unfocusedTextColor = OnSurface
                                    ),
                                    minLines = 4,
                                    maxLines = 6
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        PromptActionPill(
                                            label = "Enhance",
                                            icon = Icons.Default.AutoFixHigh,
                                            textColor = Secondary,
                                            onClick = { viewModel.enhancePrompt() }
                                        )
                                        PromptActionPill(
                                            label = "Suggest Tone",
                                            icon = Icons.Default.Lightbulb,
                                            textColor = Tertiary,
                                            onClick = { viewModel.suggestTone() }
                                        )
                                        PromptActionPill(
                                            label = "Template",
                                            icon = Icons.Default.ContentPaste,
                                            textColor = OnSurfaceVariant,
                                            onClick = {
                                                viewModel.setPrompt("Create a 60-second educational video explaining Artificial Intelligence to Grade 10 students with vivid metaphors.")
                                                viewModel.showToast("Template prompt inserted")
                                            }
                                        )
                                    }

                                    IconButton(
                                        onClick = { viewModel.clearPrompt() },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Clear",
                                            tint = Outline,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Target Duration Section
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Timelapse,
                                    contentDescription = null,
                                    tint = Tertiary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "Target Duration",
                                    color = OnSurface,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(TertiaryContainer.copy(alpha = 0.2f))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "Standard Pace",
                                    color = Tertiary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            DurationOption.entries.forEach { option ->
                                val isSelected = config.durationOption == option
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected) PrimaryContainer else SurfaceContainer
                                    ),
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { viewModel.setDuration(option) }
                                        .testTag("duration_${option.name.lowercase()}")
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 10.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = option.label,
                                            color = if (isSelected) Color.White else OnSurface,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = option.subLabel,
                                            color = if (isSelected) Color.White.copy(alpha = 0.8f) else Outline,
                                            fontSize = 11.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Aspect Ratio Section
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Crop,
                                    contentDescription = null,
                                    tint = Secondary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "Aspect Ratio & Frame",
                                    color = OnSurface,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Text(
                                text = config.aspectRatio.ratio + " " + config.aspectRatio.label,
                                color = Outline,
                                fontSize = 11.sp
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AspectRatioOption.entries.forEach { ratio ->
                                val isSelected = config.aspectRatio == ratio
                                val bg = if (isSelected) SecondaryContainer else SurfaceContainer
                                val fg = if (isSelected) OnSecondary else OnSurface

                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = bg),
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { viewModel.setAspectRatio(ratio) }
                                        .testTag("aspect_ratio_${ratio.name.lowercase()}")
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        // Shape preview
                                        Box(
                                            modifier = Modifier
                                                .height(28.dp)
                                                .width(
                                                    when (ratio) {
                                                        AspectRatioOption.LANDSCAPE_16_9 -> 38.dp
                                                        AspectRatioOption.REELS_9_16 -> 20.dp
                                                        AspectRatioOption.SQUARE_1_1 -> 26.dp
                                                    }
                                                )
                                                .clip(RoundedCornerShape(3.dp))
                                                .background(
                                                    if (isSelected) OnSecondary.copy(alpha = 0.25f)
                                                    else SurfaceContainerHighest
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = when (ratio) {
                                                    AspectRatioOption.LANDSCAPE_16_9 -> Icons.Default.Tv
                                                    AspectRatioOption.REELS_9_16 -> Icons.Default.Smartphone
                                                    AspectRatioOption.SQUARE_1_1 -> Icons.Default.CropSquare
                                                },
                                                contentDescription = null,
                                                tint = fg,
                                                modifier = Modifier.size(13.dp)
                                            )
                                        }

                                        Text(
                                            text = ratio.ratio,
                                            color = fg,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = ratio.label,
                                            color = if (isSelected) fg.copy(alpha = 0.8f) else Outline,
                                            fontSize = 10.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Aesthetic & Visual Style Section
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Palette,
                                    contentDescription = null,
                                    tint = Tertiary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "Aesthetic & Visual Style",
                                    color = OnSurface,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Text("6 Styles", color = Tertiary, fontSize = 11.sp)
                        }

                        // 2-Column Visual Styles
                        val styles = VisualStyleOption.entries
                        for (i in styles.indices step 2) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                VisualStyleCard(
                                    style = styles[i],
                                    isSelected = config.visualStyle == styles[i],
                                    onClick = { viewModel.setVisualStyle(styles[i]) },
                                    modifier = Modifier.weight(1f)
                                )
                                if (i + 1 < styles.size) {
                                    VisualStyleCard(
                                        style = styles[i + 1],
                                        isSelected = config.visualStyle == styles[i + 1],
                                        onClick = { viewModel.setVisualStyle(styles[i + 1]) },
                                        modifier = Modifier.weight(1f)
                                    )
                                } else {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }

                // Voiceover & Audio Design Section
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.RecordVoiceOver,
                                    contentDescription = null,
                                    tint = Secondary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "Voiceover & Audio Design",
                                    color = OnSurface,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Text("Multitrack AI", color = Outline, fontSize = 11.sp)
                        }

                        // Voice Persona Cards
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            VoicePersonaOption.entries.forEach { persona ->
                                val isSelected = config.voicePersona == persona
                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected) SurfaceContainerHigh else SurfaceContainer
                                    ),
                                    border = if (isSelected) androidx.compose.foundation.BorderStroke(1.dp, Secondary.copy(alpha = 0.5f)) else null,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { viewModel.setVoicePersona(persona) }
                                        .testTag("voice_${persona.nameTag.lowercase()}")
                                ) {
                                    Column(
                                        modifier = Modifier.padding(12.dp),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(
                                                        if (isSelected) SecondaryContainer
                                                        else SurfaceContainerHighest
                                                    )
                                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                            ) {
                                                Text(
                                                    text = persona.gender,
                                                    color = if (isSelected) OnSecondaryContainer else Outline,
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            }

                                            Icon(
                                                imageVector = if (isSelected) Icons.Default.VolumeUp else Icons.Default.PlayCircle,
                                                contentDescription = null,
                                                tint = if (isSelected) Secondary else Outline,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }

                                        Text(
                                            text = persona.nameTag,
                                            color = OnSurface,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold
                                        )

                                        Text(
                                            text = persona.description,
                                            color = OnSurfaceVariant,
                                            fontSize = 11.sp
                                        )
                                    }
                                }
                            }
                        }

                        // Spoken Language Card
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape)
                                            .background(SurfaceContainerHigh),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("🇺🇸", fontSize = 16.sp)
                                    }
                                    Column {
                                        Text("Spoken Language", color = Outline, fontSize = 11.sp)
                                        Text(
                                            config.language,
                                            color = OnSurface,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(SurfaceContainerHigh)
                                        .clickable { viewModel.showToast("Language set to English (US)") }
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                                    ) {
                                        Text("Change", color = Primary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                        Icon(
                                            imageVector = Icons.Default.ExpandMore,
                                            contentDescription = null,
                                            tint = Primary,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            }
                        }

                        // Background Music Card
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(PrimaryContainer)
                                            .clickable { viewModel.toggleAudioPreview() },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = if (isAudioPreviewing) Icons.Default.Pause else Icons.Default.PlayArrow,
                                            contentDescription = "Preview",
                                            tint = OnPrimary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }

                                    Column {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Text("Background Score", color = Tertiary, fontSize = 11.sp)
                                            Text("•", color = Outline, fontSize = 11.sp)
                                            Text(config.backgroundScoreDuck, color = Outline, fontSize = 10.sp)
                                        }
                                        Text(
                                            config.backgroundScore,
                                            color = OnSurface,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }

                                IconButton(
                                    onClick = { viewModel.showToast("Audio mixer opened") },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Tune,
                                        contentDescription = "Mixer",
                                        tint = OnSurfaceVariant,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Estimated Render Time Banner
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(SurfaceContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Speed,
                                        contentDescription = null,
                                        tint = Secondary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }

                                Column {
                                    Text(
                                        "Estimated Render Time",
                                        color = OnSurface,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        "~42 seconds using Turbo H100 Cluster",
                                        color = OnSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(TertiaryContainer.copy(alpha = 0.2f))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    "Fast",
                                    color = Tertiary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                // Action Bar Dock Space
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }

        // Bottom Persistent Dock: Cost & Balance + Generate Video CTA
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 84.dp)
        ) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SurfaceContainerHigh.copy(alpha = 0.95f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Token,
                                contentDescription = null,
                                tint = Tertiary,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                "Cost: 15 Credits",
                                color = OnSurface,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            "Balance: 120 available",
                            color = OnSurfaceVariant,
                            fontSize = 10.sp
                        )
                    }

                    Button(
                        onClick = onStartGeneration,
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer),
                        shape = RoundedCornerShape(22.dp),
                        modifier = Modifier
                            .height(44.dp)
                            .testTag("studio_generate_video_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                "Generate Video",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PromptActionPill(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    textColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainerHigh)
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(12.dp)
            )
            Text(label, color = textColor, fontSize = 11.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun VisualStyleCard(
    style: VisualStyleOption,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) SurfaceContainerHigh else SurfaceContainer
        ),
        border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, Tertiary) else null,
        modifier = modifier
            .clickable { onClick() }
            .testTag("style_${style.name.lowercase()}")
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(SurfaceContainerLowest)
            ) {
                AsyncImage(
                    model = style.imageUrl,
                    contentDescription = style.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Tertiary)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = OnTertiary,
                                modifier = Modifier.size(10.dp)
                            )
                            Text(
                                "Selected",
                                color = OnTertiary,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = style.title,
                        color = OnSurface,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        imageVector = when (style) {
                            VisualStyleOption.EDUCATIONAL -> Icons.Default.School
                            VisualStyleOption.CINEMATIC -> Icons.Default.MovieFilter
                            VisualStyleOption.CORPORATE -> Icons.Default.BusinessCenter
                            VisualStyleOption.SOCIAL_MEDIA -> Icons.Default.TrendingUp
                            VisualStyleOption.EXPLAINER -> Icons.Default.ModelTraining
                            VisualStyleOption.CARTOON -> Icons.Default.Draw
                        },
                        contentDescription = null,
                        tint = if (isSelected) Tertiary else Outline,
                        modifier = Modifier.size(14.dp)
                    )
                }

                Text(
                    text = style.description,
                    color = OnSurfaceVariant,
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
