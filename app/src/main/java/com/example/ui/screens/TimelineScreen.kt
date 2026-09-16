package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Redo
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.Transform
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.Constants
import com.example.ui.components.TopBar
import com.example.ui.theme.OnPrimary
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
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.Tertiary
import com.example.ui.theme.TertiaryContainer
import com.example.ui.viewmodel.VideoStudioViewModel

@Composable
fun TimelineScreen(
    viewModel: VideoStudioViewModel,
    onBackClick: () -> Unit,
    onExportClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedScene by viewModel.selectedTimelineScene.collectAsState()
    val isPlaying by viewModel.isTimelinePlaying.collectAsState()
    val timelinePos by viewModel.timelinePositionSeconds.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Surface)
            .testTag("timeline_screen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Top Bar with Undo/Redo & Export Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Surface.copy(alpha = 0.95f))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Movie,
                                contentDescription = "Back",
                                tint = OnSurface,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "Timeline Editor",
                                color = OnSurface,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Project #824 • 9:16 Shorts",
                                color = OnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        IconButton(
                            onClick = { viewModel.showToast("Action undone") },
                            modifier = Modifier.size(30.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Undo,
                                contentDescription = "Undo",
                                tint = OnSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        IconButton(
                            onClick = { viewModel.showToast("Action redone") },
                            modifier = Modifier.size(30.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Redo,
                                contentDescription = "Redo",
                                tint = OnSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Button(
                            onClick = onExportClick,
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer),
                            shape = RoundedCornerShape(16.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("timeline_export_button")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text("Export", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Icon(
                                    imageVector = Icons.Default.Download,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Viewport Player Card
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            AsyncImage(
                                model = Constants.TIMELINE_PREVIEW,
                                contentDescription = "Timeline Viewport",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            // Top Specs Badge
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(10.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.Black.copy(alpha = 0.7f))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(6.dp)
                                            .clip(CircleShape)
                                            .background(Tertiary)
                                    )
                                    Text("4K UHD • 60 FPS", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            // Dynamic Subtitle Karaoke overlay
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(horizontal = 24.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.Black.copy(alpha = 0.65f))
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "That is exactly how Artificial Intelligence works.",
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }

                            // Bottom HUD Controls
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth()
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f))
                                        )
                                    )
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
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
                                        IconButton(
                                            onClick = { viewModel.showToast("Replayed 10s") },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Replay10,
                                                contentDescription = "-10s",
                                                tint = Color.White,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }

                                        Box(
                                            modifier = Modifier
                                                .size(34.dp)
                                                .clip(CircleShape)
                                                .background(PrimaryContainer)
                                                .clickable { viewModel.toggleTimelinePlay() },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                                contentDescription = "Play",
                                                tint = Color.White,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }

                                        IconButton(
                                            onClick = { viewModel.showToast("Forwarded 10s") },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Forward10,
                                                contentDescription = "+10s",
                                                tint = Color.White,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }

                                    Text(
                                        text = "00:14.20 / 01:00.00",
                                        color = Color.White,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium
                                    )

                                    IconButton(
                                        onClick = { viewModel.showToast("Fullscreen preview") },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Fullscreen,
                                            contentDescription = "Fullscreen",
                                            tint = Color.White,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Multitrack Studio Section
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Timeline Ruler Header
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("00:00", color = Outline, fontSize = 10.sp)
                                Text("00:15", color = Outline, fontSize = 10.sp)
                                Text("00:30", color = Outline, fontSize = 10.sp)
                                Text("00:45", color = Outline, fontSize = 10.sp)
                                Text("01:00", color = Outline, fontSize = 10.sp)
                            }

                            // Track 1: Visuals Track
                            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("TRACK 1: VISUALS", color = Outline, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    Text("6 Scenes", color = Primary, fontSize = 10.sp)
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(34.dp),
                                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                                ) {
                                    val visualScenes = listOf(
                                        1 to "S1 (10s)",
                                        2 to "S2 (10s)",
                                        3 to "S3 (12s)",
                                        4 to "S4 (10s)",
                                        5 to "S5 (10s)",
                                        6 to "S6 (8s)"
                                    )

                                    visualScenes.forEach { (num, label) ->
                                        val isCurrentSelected = num == selectedScene
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(34.dp)
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(
                                                    if (isCurrentSelected) PrimaryContainer
                                                    else SurfaceContainerHigh
                                                )
                                                .border(
                                                    width = if (isCurrentSelected) 2.dp else 0.dp,
                                                    color = if (isCurrentSelected) Tertiary else Color.Transparent,
                                                    shape = RoundedCornerShape(6.dp)
                                                )
                                                .clickable { viewModel.selectTimelineScene(num) },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = label,
                                                color = if (isCurrentSelected) Color.White else OnSurfaceVariant,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }
                                    }
                                }
                            }

                            // Track 2: Voiceover Track
                            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("TRACK 2: ARIA (TTS)", color = Outline, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    Text("Auto-Ducked -12dB", color = Secondary, fontSize = 10.sp)
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(28.dp),
                                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .weight(2f)
                                            .height(28.dp)
                                            .clip(RoundedCornerShape(5.dp))
                                            .background(SecondaryContainer)
                                            .padding(horizontal = 6.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        Text("Voiceover Part 1", color = OnSecondaryContainer, fontSize = 9.sp)
                                    }

                                    Box(
                                        modifier = Modifier
                                            .weight(2f)
                                            .height(28.dp)
                                            .clip(RoundedCornerShape(5.dp))
                                            .background(SecondaryContainer.copy(alpha = 0.7f))
                                            .padding(horizontal = 6.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        Text("Voiceover Part 2", color = OnSecondaryContainer, fontSize = 9.sp)
                                    }

                                    Box(
                                        modifier = Modifier
                                            .weight(2f)
                                            .height(28.dp)
                                            .clip(RoundedCornerShape(5.dp))
                                            .background(SecondaryContainer.copy(alpha = 0.5f))
                                            .padding(horizontal = 6.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        Text("Voiceover Part 3", color = OnSecondaryContainer, fontSize = 9.sp)
                                    }
                                }
                            }

                            // Track 3: Captions
                            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                Text("TRACK 3: DYNAMIC CAPTIONS", color = Outline, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(24.dp),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    listOf("That", "is", "how", "Artificial", "Intelligence", "works!").forEach { word ->
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(TertiaryContainer.copy(alpha = 0.3f))
                                                .padding(horizontal = 6.dp, vertical = 2.dp)
                                        ) {
                                            Text(word, color = Tertiary, fontSize = 9.sp, fontWeight = FontWeight.SemiBold)
                                        }
                                    }
                                }
                            }

                            // Track 4: Audio Waveform
                            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                Text("TRACK 4: LO-FI STUDY SYNTH", color = Outline, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(28.dp)
                                        .clip(RoundedCornerShape(5.dp))
                                        .background(SurfaceContainerHigh)
                                        .padding(horizontal = 8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val heights = listOf(14, 22, 10, 18, 26, 12, 20, 8, 24, 16, 28, 14, 22, 12, 26, 18, 14, 20, 16, 24, 12, 18)
                                        heights.forEach { h ->
                                            Box(
                                                modifier = Modifier
                                                    .width(3.dp)
                                                    .height(h.dp)
                                                    .clip(RoundedCornerShape(1.dp))
                                                    .background(Primary.copy(alpha = 0.7f))
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Selected Scene Quick Tools
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Selected Scene $selectedScene Tools",
                            color = OnSurface,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            SceneToolCard(
                                icon = Icons.Default.ContentCut,
                                label = "Split",
                                onClick = { viewModel.showToast("Scene split at 00:14.20") },
                                modifier = Modifier.weight(1f)
                            )
                            SceneToolCard(
                                icon = Icons.Default.Refresh,
                                label = "Regen Visual",
                                onClick = { viewModel.showToast("Regenerating scene visual...") },
                                modifier = Modifier.weight(1f)
                            )
                            SceneToolCard(
                                icon = Icons.Default.Subtitles,
                                label = "Edit Subtitle",
                                onClick = { viewModel.showToast("Subtitle editor opened") },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            SceneToolCard(
                                icon = Icons.Default.Schedule,
                                label = "Trim Duration",
                                onClick = { viewModel.showToast("Trim handles enabled") },
                                modifier = Modifier.weight(1f)
                            )
                            SceneToolCard(
                                icon = Icons.Default.Transform,
                                label = "Transition",
                                onClick = { viewModel.showToast("Cross-dissolve transition added") },
                                modifier = Modifier.weight(1f)
                            )
                            SceneToolCard(
                                icon = Icons.Default.Add,
                                label = "Add Scene",
                                onClick = { viewModel.addScene() },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Bottom Space for Floating Dock
                item {
                    Spacer(modifier = Modifier.height(70.dp))
                }
            }
        }

        // Bottom Bar Dock: Save Draft & Preview Full Video
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 84.dp)
        ) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerHigh.copy(alpha = 0.96f)),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { viewModel.showToast("Draft saved successfully!") },
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text("Save Draft", color = OnSurface, fontSize = 12.sp)
                    }

                    Button(
                        onClick = onExportClick,
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("Preview Full Video", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SceneToolCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
        modifier = modifier
            .clickable { onClick() }
            .testTag("tool_${label.lowercase().replace(" ", "_")}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Secondary,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = label,
                color = OnSurface,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
