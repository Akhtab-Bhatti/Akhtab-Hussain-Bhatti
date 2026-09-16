package com.example.ui.screens

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Subtitles
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.Constants
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
fun ExportScreen(
    viewModel: VideoStudioViewModel,
    onBackClick: () -> Unit,
    onEditAgain: () -> Unit,
    onCreateNew: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isPlayingVideo by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Surface)
            .testTag("export_screen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar
            TopBar(
                title = "Export Complete",
                subtitle = "Explaining AI to Grade 10",
                showBackButton = true,
                onBackClick = onBackClick,
                showNotifications = false,
                showMore = true
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Status Pill
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(TertiaryContainer.copy(alpha = 0.25f))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
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
                                    Text("4K Render Ready", color = Tertiary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
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
                                Icon(
                                    imageVector = Icons.Default.Speed,
                                    contentDescription = null,
                                    tint = Secondary,
                                    modifier = Modifier.size(12.dp)
                                )
                                Text("42s render time", color = OnSurfaceVariant, fontSize = 11.sp)
                            }
                        }
                    }
                }

                // 9:16 Video Player Hero Card
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(290.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            AsyncImage(
                                model = Constants.EXPORT_PREVIEW,
                                contentDescription = "Finished Video",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )

                            // Top Badges
                            Row(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color.Black.copy(alpha = 0.7f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text("4K UHD • 60 FPS", color = Tertiary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(Color.Black.copy(alpha = 0.7f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text("Dolby 48kHz", color = Secondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            // Center Glowing Play Button
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(PrimaryContainer.copy(alpha = 0.9f))
                                    .clickable {
                                        isPlayingVideo = !isPlayingVideo
                                        viewModel.showToast(if (isPlayingVideo) "Playing finished video" else "Paused")
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play",
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            // Bottom HUD Bar
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
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    // Scrubber line
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(3.dp)
                                            .clip(RoundedCornerShape(2.dp))
                                            .background(Color.White.copy(alpha = 0.3f))
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth(0.98f)
                                                .height(3.dp)
                                                .background(Tertiary)
                                        )
                                    }

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
                                                imageVector = Icons.Default.Replay,
                                                contentDescription = "Replay",
                                                tint = Color.White,
                                                modifier = Modifier
                                                    .size(18.dp)
                                                    .clickable { viewModel.showToast("Replaying") }
                                            )
                                            Text("01:00 / 01:00", color = Color.White, fontSize = 11.sp)
                                        }

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(Color.Black.copy(alpha = 0.6f))
                                                    .padding(horizontal = 5.dp, vertical = 1.dp)
                                            ) {
                                                Text("9:16", color = Color.White, fontSize = 10.sp)
                                            }
                                            Icon(
                                                imageVector = Icons.Default.Fullscreen,
                                                contentDescription = "Fullscreen",
                                                tint = Color.White,
                                                modifier = Modifier
                                                    .size(18.dp)
                                                    .clickable { viewModel.showToast("Fullscreen playback") }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Primary Download Button & Formats
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { viewModel.showToast("Downloading 4K Master MP4 (28.4 MB)...") },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer),
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("download_4k_button")
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Download,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    "Download Video (MP4 • 4K)",
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Secondary format pills
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FormatPill(
                                label = "1080p Web",
                                icon = Icons.Default.Movie,
                                onClick = { viewModel.showToast("Downloading 1080p Web MP4 (11.2 MB)...") },
                                modifier = Modifier.weight(1f)
                            )
                            FormatPill(
                                label = "Captions .SRT",
                                icon = Icons.Default.Subtitles,
                                onClick = { viewModel.showToast("Downloaded Captions .SRT file") },
                                modifier = Modifier.weight(1f)
                            )
                            FormatPill(
                                label = "Audio .MP3",
                                icon = Icons.Default.GraphicEq,
                                onClick = { viewModel.showToast("Downloaded Master Audio .MP3") },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Project Render Specs Card
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
                            Text(
                                "Project Render Specs",
                                color = OnSurface,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                SpecItem(label = "Duration", value = "60 Seconds")
                                SpecItem(label = "Aspect Ratio", value = "9:16 Shorts")
                                SpecItem(label = "Resolution", value = "1080x1920 4K")
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                SpecItem(label = "Voice Persona", value = "English Aria AI")
                                SpecItem(label = "File Size", value = "28.4 MB")
                                SpecItem(label = "Visual Style", value = "Educational")
                            }
                        }
                    }
                }

                // Publish Directly (Social Media)
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "Publish Directly",
                            color = OnSurface,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            SocialSharePill("TikTok", "🎵", onClick = { viewModel.showToast("Shared to TikTok!") }, modifier = Modifier.weight(1f))
                            SocialSharePill("Shorts", "▶️", onClick = { viewModel.showToast("Shared to YouTube Shorts!") }, modifier = Modifier.weight(1f))
                            SocialSharePill("Reels", "📸", onClick = { viewModel.showToast("Shared to Instagram Reels!") }, modifier = Modifier.weight(1f))
                            SocialSharePill("Copy Link", "🔗", onClick = { viewModel.showToast("Link copied to clipboard!") }, modifier = Modifier.weight(1f))
                        }
                    }
                }

                // Magic AI Helper: Captions & Tags
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
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
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = Tertiary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        "AI Social Post & Hashtags",
                                        color = OnSurface,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Text(
                                    "Copy All",
                                    color = Primary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.clickable { viewModel.showToast("Captions & Hashtags copied!") }
                                )
                            }

                            Text(
                                text = "Demystifying Artificial Intelligence for high school students in 60 seconds! 🧠✨ From neural pathways to cat pictures, here's how AI actually learns.",
                                color = OnSurfaceVariant,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                listOf("#AI", "#NeuralNetworks", "#STEM", "#TechEd").forEach { tag ->
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(SurfaceContainerHighest)
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(tag, color = Tertiary, fontSize = 11.sp)
                                    }
                                }
                            }
                        }
                    }
                }

                // Post-Production Navigation Actions
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = onEditAgain,
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    tint = OnSurface,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text("Edit Again in Timeline", color = OnSurface, fontSize = 13.sp)
                            }
                        }

                        Button(
                            onClick = onCreateNew,
                            colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHigh),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text("Create New Video from Prompt", color = Primary, fontSize = 13.sp)
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(70.dp))
                }
            }
        }
    }
}

@Composable
fun FormatPill(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerHigh),
        modifier = modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = OnSurfaceVariant,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(label, color = OnSurface, fontSize = 11.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun SpecItem(label: String, value: String) {
    Column {
        Text(label, color = Outline, fontSize = 10.sp)
        Text(value, color = OnSurface, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun SocialSharePill(
    name: String,
    emoji: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerHigh),
        modifier = modifier.clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(emoji, fontSize = 14.sp)
            Text(name, color = OnSurface, fontSize = 10.sp, fontWeight = FontWeight.Medium)
        }
    }
}
