package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.SceneItem
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
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.Tertiary
import com.example.ui.theme.TertiaryContainer
import com.example.ui.viewmodel.VideoStudioViewModel

@Composable
fun StoryboardScreen(
    viewModel: VideoStudioViewModel,
    onBackClick: () -> Unit,
    onOpenTimeline: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scenes by viewModel.scenes.collectAsState()
    var isExpandedScenesCollapsed by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Surface)
            .testTag("storyboard_screen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar
            TopBar(
                title = "Storyboard Scenes",
                showBackButton = true,
                onBackClick = onBackClick,
                showNotifications = false,
                showMore = true
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Header & Add Scene Button
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                PulsingDot(color = Tertiary)
                                Text(
                                    text = "LIVE STORYBOARD",
                                    color = Tertiary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "${scenes.size} Scenes • 60s Total",
                                color = OnSurface,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Educational Style • 9:16 Shorts",
                                color = OnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        }

                        Button(
                            onClick = { viewModel.addScene() },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer),
                            shape = RoundedCornerShape(18.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            modifier = Modifier.testTag("add_scene_button")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text("Add Scene", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // 60s Reel Pacing Segmented Timeline Bar
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "60s Reel Pacing",
                                    color = OnSurface,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Tertiary,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Text(
                                        text = "100% Generated",
                                        color = Tertiary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            // 6-Segment visual bar
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp)
                                    .clip(RoundedCornerShape(5.dp)),
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                val sceneDurations = listOf(10, 10, 12, 10, 10, 8)
                                val colors = listOf(
                                    PrimaryContainer,
                                    Primary,
                                    SecondaryContainer,
                                    Secondary,
                                    TertiaryContainer,
                                    Tertiary
                                )
                                sceneDurations.forEachIndexed { idx, dur ->
                                    Box(
                                        modifier = Modifier
                                            .weight(dur.toFloat())
                                            .fillMaxSize()
                                            .background(colors[idx % colors.size])
                                    )
                                }
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("00:00", color = Outline, fontSize = 10.sp)
                                Text("00:20", color = Outline, fontSize = 10.sp)
                                Text("00:40", color = Outline, fontSize = 10.sp)
                                Text("01:00", color = Outline, fontSize = 10.sp)
                            }
                        }
                    }
                }

                // Toolbar Actions
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ToolbarPill(
                            label = "Bulk Regenerate",
                            icon = Icons.Default.Refresh,
                            onClick = { viewModel.showToast("Regenerating all scene frames...") }
                        )
                        ToolbarPill(
                            label = "Reorder",
                            icon = Icons.Default.SwapVert,
                            onClick = { viewModel.showToast("Drag handles enabled to reorder") }
                        )
                        ToolbarPill(
                            label = "Preview Audio",
                            icon = Icons.Default.VolumeUp,
                            onClick = { viewModel.toggleAudioPreview() }
                        )
                    }
                }

                // Scene Cards
                items(scenes) { scene ->
                    SceneCard(
                        scene = scene,
                        onVoiceoverChange = { viewModel.updateSceneVoiceover(scene.id, it) },
                        onDeleteClick = { viewModel.removeScene(scene.id) },
                        onRegenerate = { viewModel.showToast("Regenerating Scene ${scene.sceneNumber}...") },
                        onEditPrompt = { viewModel.showToast("Editing prompt for Scene ${scene.sceneNumber}") },
                        onPlayClip = { onOpenTimeline() }
                    )
                }

                // Collapsed Extra Scenes Preview
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerHigh),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { isExpandedScenesCollapsed = !isExpandedScenesCollapsed }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Scenes 04, 05 & 06 (28s Total)",
                                    color = OnSurface,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Pre-rendered & ready in multitrack timeline",
                                    color = OnSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            }
                            Icon(
                                imageVector = if (isExpandedScenesCollapsed) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null,
                                tint = OnSurfaceVariant
                            )
                        }
                    }
                }

                // Bottom Space for Dock
                item {
                    Spacer(modifier = Modifier.height(70.dp))
                }
            }
        }

        // Bottom Action Dock
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
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = { viewModel.showToast("All scenes sent for regeneration") },
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("Regenerate All", color = OnSurface, fontSize = 12.sp)
                    }

                    Button(
                        onClick = onOpenTimeline,
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("open_in_timeline_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("Open in Video Editor", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ToolbarPill(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainerHigh)
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = OnSurfaceVariant,
                modifier = Modifier.size(14.dp)
            )
            Text(text = label, color = OnSurface, fontSize = 11.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun SceneCard(
    scene: SceneItem,
    onVoiceoverChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    onRegenerate: () -> Unit,
    onEditPrompt: () -> Unit,
    onPlayClip: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("scene_card_${scene.sceneNumber}")
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header Row: Drag Handle + Scene Title + Badges + Delete
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
                        imageVector = Icons.Default.DragHandle,
                        contentDescription = "Drag to reorder",
                        tint = Outline,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Scene 0${scene.sceneNumber}",
                        color = OnSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = scene.timeRange,
                        color = OnSurfaceVariant,
                        fontSize = 11.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (scene.isActive) TertiaryContainer else SecondaryContainer)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = if (scene.isActive) "Active" else "Ready",
                            color = if (scene.isActive) OnTertiary else OnSecondaryContainer,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    IconButton(
                        onClick = onDeleteClick,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Outline,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            // Middle Row: Video Thumbnail + Visual Prompt
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // 9:16 Video Preview
                Box(
                    modifier = Modifier
                        .width(85.dp)
                        .height(115.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLowest)
                        .clickable { onPlayClip() }
                ) {
                    AsyncImage(
                        model = scene.imageUrl,
                        contentDescription = "Scene ${scene.sceneNumber}",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Tag
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.Black.copy(alpha = 0.7f))
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text("AI Shot", color = Tertiary, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                    }

                    // Play Button
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(26.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.6f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // Prompt & Seed
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Visual Prompt", color = Outline, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        Text("Seed ${scene.seed}", color = Outline, fontSize = 10.sp)
                    }

                    Text(
                        text = scene.visualPrompt,
                        color = OnSurface,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        PromptActionPill(
                            label = "Regenerate",
                            icon = Icons.Default.Refresh,
                            textColor = Primary,
                            onClick = onRegenerate
                        )
                        PromptActionPill(
                            label = "Edit Prompt",
                            icon = Icons.Default.Edit,
                            textColor = Secondary,
                            onClick = onEditPrompt
                        )
                    }
                }
            }

            // Voiceover Script Box
            Card(
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerHigh),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.RecordVoiceOver,
                                contentDescription = null,
                                tint = Secondary,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = "Voiceover: ${scene.voiceoverVoice}",
                                color = Secondary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Text(
                            text = "${scene.wordCount} words",
                            color = Outline,
                            fontSize = 10.sp
                        )
                    }

                    OutlinedTextField(
                        value = scene.voiceoverText,
                        onValueChange = onVoiceoverChange,
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedTextColor = OnSurface,
                            unfocusedTextColor = OnSurface
                        ),
                        minLines = 2,
                        maxLines = 3
                    )

                    // Captions Pill
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(SurfaceContainerLowest)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Captions: " + scene.captionsText,
                            color = OnSurfaceVariant,
                            fontSize = 11.sp,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    }
                }
            }
        }
    }
}
