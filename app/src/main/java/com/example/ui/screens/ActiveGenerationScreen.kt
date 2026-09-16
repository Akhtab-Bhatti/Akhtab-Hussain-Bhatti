package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.ViewKanban
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.TopBar
import com.example.ui.theme.OnPrimaryContainer
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
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.Tertiary
import com.example.ui.theme.TertiaryContainer
import com.example.ui.viewmodel.VideoStudioViewModel

@Composable
fun ActiveGenerationScreen(
    viewModel: VideoStudioViewModel,
    onBackClick: () -> Unit,
    onViewStoryboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val genState by viewModel.generationState.collectAsState()
    val studioConfig by viewModel.studioConfig.collectAsState()

    val animatedProgress by animateFloatAsState(
        targetValue = genState.progressPercent / 100f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "gen_progress"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "spin_ring")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Surface)
            .testTag("active_generation_screen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar
            TopBar(
                title = "Active Generation",
                showBackButton = true,
                onBackClick = onBackClick,
                showNotifications = false,
                showMore = true
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Radial Dial Section
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Circular Progress Dial
                        Box(
                            modifier = Modifier.size(190.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val strokeWidth = 12.dp.toPx()
                                // Background Track
                                drawCircle(
                                    color = Color(0xFF1F2433),
                                    style = Stroke(width = strokeWidth)
                                )
                                // Active Progress Arc
                                drawArc(
                                    brush = Brush.sweepGradient(
                                        listOf(Tertiary, Primary, Secondary, Tertiary)
                                    ),
                                    startAngle = -90f,
                                    sweepAngle = 360f * animatedProgress,
                                    useCenter = false,
                                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "${genState.progressPercent}%",
                                    color = OnSurface,
                                    fontSize = 42.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            if (genState.isComplete) TertiaryContainer
                                            else SecondaryContainer.copy(alpha = 0.6f)
                                        )
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        PulsingDot(
                                            color = if (genState.isComplete) Tertiary else Secondary
                                        )
                                        Text(
                                            text = if (genState.isComplete) "READY" else "RENDERING",
                                            color = if (genState.isComplete) Tertiary else Secondary,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 0.5.sp
                                        )
                                    }
                                }
                            }
                        }

                        // Headline
                        Text(
                            text = if (genState.isComplete) "Your Masterpiece is Ready!" else "Creating Your Masterpiece...",
                            color = OnSurface,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = if (genState.isComplete) genState.currentStageText
                            else "${genState.currentStageText} • ~${genState.estimatedSecondsRemaining}s left",
                            color = OnSurfaceVariant,
                            fontSize = 13.sp
                        )

                        val error = genState.errorMessage
                        if (error != null) {
                            Box(
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.8f))
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = error,
                                    color = MaterialTheme.colorScheme.onErrorContainer,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                // Active Prompt Specification Card
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Active Prompt Specification",
                                    color = Primary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Text(
                                text = studioConfig.prompt,
                                color = OnSurface,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                TagPill("${studioConfig.durationOption.label} Duration")
                                TagPill("${studioConfig.aspectRatio.ratio} Reels")
                                TagPill(studioConfig.visualStyle.title)
                            }
                        }
                    }
                }

                // Generation Workflow Pipeline (6 Stages)
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Generation Pipeline",
                            color = OnSurface,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )

                        val stages = listOf(
                            "Understanding Prompt & Concept" to "Deconstructed semantics & pacing",
                            "Creating Script & Storyboard" to "Structured 6 cinematic scenes",
                            "Generating AI Scene Visuals" to genState.currentStageText,
                            "Synthesizing Neural Voiceover" to "Nova 48kHz voice model queued",
                            "Syncing Dynamic Captions & Soundtrack" to "Karaoke timing & audio ducking",
                            "Master 4K Video Rendering" to "H.265 high-bitrate encoding"
                        )

                        stages.forEachIndexed { index, (stageTitle, stageSub) ->
                            val stageNum = index + 1
                            val status = when {
                                stageNum < genState.currentStageIndex -> PipelineStatus.COMPLETED
                                stageNum == genState.currentStageIndex -> PipelineStatus.IN_PROGRESS
                                else -> PipelineStatus.PENDING
                            }

                            PipelineStageCard(
                                stepNumber = stageNum,
                                title = stageTitle,
                                subtitle = stageSub,
                                status = status
                            )
                        }
                    }
                }

                // Creator Tip Card
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerHigh),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = Tertiary,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "While you wait: You can navigate around the app. We'll alert you as soon as your 4K render completes.",
                                color = OnSurfaceVariant,
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }

                // Action Buttons
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = onViewStoryboard,
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer),
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("view_storyboard_button")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ViewKanban,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = if (genState.isComplete) "Open Storyboard & Scenes" else "Inspect Live Storyboard",
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        OutlinedButton(
                            onClick = onBackClick,
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                        ) {
                            Text(
                                text = "Run in Background",
                                color = OnSurface,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

enum class PipelineStatus {
    COMPLETED,
    IN_PROGRESS,
    PENDING
}

@Composable
fun PipelineStageCard(
    stepNumber: Int,
    title: String,
    subtitle: String,
    status: PipelineStatus
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (status) {
                PipelineStatus.COMPLETED -> SurfaceContainer
                PipelineStatus.IN_PROGRESS -> SurfaceContainerHigh
                PipelineStatus.PENDING -> SurfaceContainerLow
            }
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Status Icon
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(
                        when (status) {
                            PipelineStatus.COMPLETED -> TertiaryContainer
                            PipelineStatus.IN_PROGRESS -> PrimaryContainer
                            PipelineStatus.PENDING -> SurfaceContainerHighest
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                when (status) {
                    PipelineStatus.COMPLETED -> Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Done",
                        tint = OnTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                    PipelineStatus.IN_PROGRESS -> Icon(
                        imageVector = Icons.Default.Sync,
                        contentDescription = "Running",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    PipelineStatus.PENDING -> Text(
                        text = "$stepNumber",
                        color = OnSurfaceVariant,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = if (status == PipelineStatus.PENDING) OnSurfaceVariant else OnSurface,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
                    color = when (status) {
                        PipelineStatus.IN_PROGRESS -> Tertiary
                        else -> OnSurfaceVariant
                    },
                    fontSize = 11.sp
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        when (status) {
                            PipelineStatus.COMPLETED -> TertiaryContainer.copy(alpha = 0.2f)
                            PipelineStatus.IN_PROGRESS -> PrimaryContainer.copy(alpha = 0.2f)
                            PipelineStatus.PENDING -> Color.Transparent
                        }
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = when (status) {
                        PipelineStatus.COMPLETED -> "Done"
                        PipelineStatus.IN_PROGRESS -> "Running"
                        PipelineStatus.PENDING -> "Waiting"
                    },
                    color = when (status) {
                        PipelineStatus.COMPLETED -> Tertiary
                        PipelineStatus.IN_PROGRESS -> Primary
                        PipelineStatus.PENDING -> Outline
                    },
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
