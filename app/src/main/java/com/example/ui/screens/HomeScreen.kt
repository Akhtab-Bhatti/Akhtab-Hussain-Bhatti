package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.filled.ViewTimeline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.Constants
import com.example.data.model.ProjectItem
import com.example.data.model.ProjectStatus
import com.example.ui.components.TopBar
import com.example.ui.theme.Error
import com.example.ui.theme.OnPrimary
import com.example.ui.theme.OnPrimaryContainer
import com.example.ui.theme.OnSecondary
import com.example.ui.theme.OnSurface
import com.example.ui.theme.OnSurfaceVariant
import com.example.ui.theme.OnTertiary
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
import com.example.ui.viewmodel.AppNavigationTab
import com.example.ui.viewmodel.VideoStudioViewModel

@Composable
fun HomeScreen(
    viewModel: VideoStudioViewModel,
    onNavigateToStudio: () -> Unit,
    onNavigateToTimeline: () -> Unit,
    onNavigateToExport: () -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredProjects by viewModel.filteredProjects.collectAsState()
    val activeFilter by viewModel.activeFilter.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val homeViewMode by viewModel.homeViewMode.collectAsState()

    var fastPrompt by remember { mutableStateOf("Cyberpunk rain city with neon reflections...") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Surface)
            .testTag("home_screen")
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Bar
            TopBar(
                title = if (homeViewMode == "dashboard") "Project Details" else "Prompt2Video",
                showBackButton = false,
                showNotifications = true,
                showMore = true,
                onMoreClick = {
                    val nextMode = if (homeViewMode == "dashboard") "showcase" else "dashboard"
                    viewModel.setHomeViewMode(nextMode)
                    viewModel.showToast("Switched to $nextMode view")
                }
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                // View Mode Switcher Pill Banner
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(SurfaceContainerHigh)
                                .padding(2.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(if (homeViewMode == "dashboard") PrimaryContainer else Color.Transparent)
                                    .clickable { viewModel.setHomeViewMode("dashboard") }
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "Creator Studio",
                                    color = if (homeViewMode == "dashboard") OnPrimaryContainer else OnSurfaceVariant,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(18.dp))
                                    .background(if (homeViewMode == "showcase") PrimaryContainer else Color.Transparent)
                                    .clickable { viewModel.setHomeViewMode("showcase") }
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "Studio Showcase",
                                    color = if (homeViewMode == "showcase") OnPrimaryContainer else OnSurfaceVariant,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        // Status Badge
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(SurfaceContainerHigh)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                PulsingDot(color = Tertiary)
                                Text(
                                    text = "GPU Ready",
                                    color = Tertiary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                if (homeViewMode == "dashboard") {
                    // --- CREATOR STUDIO DASHBOARD SECTION ---

                    // Header Greeting
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "CREATOR STUDIO",
                                color = OnSurfaceVariant,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "Welcome back, Sarah 👋",
                                color = OnSurface,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = (-0.5).sp,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }

                    // Quick Stats Strip
                    item {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Stat 1: Videos
                            item {
                                StatCard(
                                    title = "Videos",
                                    value = "24",
                                    subValue = "+3 this week",
                                    subColor = Tertiary,
                                    icon = Icons.Default.VideoLibrary,
                                    iconColor = Primary
                                )
                            }
                            // Stat 2: Credits Left
                            item {
                                StatCard(
                                    title = "Credits Left",
                                    value = "850",
                                    unit = "pts",
                                    subValue = "Tier Pro Auto",
                                    subColor = OnSurfaceVariant,
                                    icon = Icons.Default.Bolt,
                                    iconColor = Secondary
                                )
                            }
                            // Stat 3: Avg Render
                            item {
                                StatCard(
                                    title = "Avg Render",
                                    value = "42s",
                                    subValue = "⚡ Ultra-fast",
                                    subColor = Primary,
                                    icon = Icons.Default.Speed,
                                    iconColor = Tertiary
                                )
                            }
                        }
                    }

                    // Prompt Quick-Launcher Banner
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 10.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    Brush.linearGradient(
                                        listOf(
                                            PrimaryContainer,
                                            SecondaryContainer,
                                            SurfaceContainerHighest
                                        )
                                    )
                                )
                                .padding(16.dp)
                                .testTag("fast_track_banner")
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(Color.Black.copy(alpha = 0.35f))
                                            .padding(horizontal = 8.dp, vertical = 3.dp)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.AutoAwesome,
                                                contentDescription = null,
                                                tint = Tertiary,
                                                modifier = Modifier.size(13.dp)
                                            )
                                            Text(
                                                text = "Prompt2Video v4.2 Turbo",
                                                color = Color.White,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }

                                    Text(
                                        text = "FAST TRACK",
                                        color = Secondary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )
                                }

                                Text(
                                    text = "Turn your thoughts into cinematic frames in seconds",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 24.sp
                                )

                                // Input bar with Generate button
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(SurfaceContainerLowest.copy(alpha = 0.9f))
                                        .padding(4.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.EditNote,
                                            contentDescription = null,
                                            tint = OnSurfaceVariant,
                                            modifier = Modifier
                                                .padding(start = 8.dp)
                                                .size(20.dp)
                                        )

                                        OutlinedTextField(
                                            value = fastPrompt,
                                            onValueChange = { fastPrompt = it },
                                            modifier = Modifier
                                                .weight(1f)
                                                .testTag("fast_track_input"),
                                            placeholder = {
                                                Text(
                                                    "e.g., Cyberpunk rain city...",
                                                    color = OnSurfaceVariant,
                                                    fontSize = 13.sp
                                                )
                                            },
                                            colors = OutlinedTextFieldDefaults.colors(
                                                focusedBorderColor = Color.Transparent,
                                                unfocusedBorderColor = Color.Transparent,
                                                focusedTextColor = OnSurface,
                                                unfocusedTextColor = OnSurface
                                            ),
                                            singleLine = true
                                        )

                                        Button(
                                            onClick = {
                                                viewModel.setPrompt(fastPrompt)
                                                viewModel.startGeneration(fastPrompt)
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = PrimaryContainer
                                            ),
                                            shape = RoundedCornerShape(8.dp),
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                            modifier = Modifier
                                                .padding(end = 2.dp)
                                                .testTag("fast_track_generate_button")
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                                            ) {
                                                Text(
                                                    "Generate",
                                                    color = Color.White,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.SemiBold
                                                )
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

                    // Search & Filter Chips
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                        ) {
                            // Search Box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(SurfaceContainerHigh)
                                    .padding(horizontal = 12.dp, vertical = 2.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search",
                                        tint = OnSurfaceVariant,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    OutlinedTextField(
                                        value = searchQuery,
                                        onValueChange = { viewModel.setSearchQuery(it) },
                                        placeholder = {
                                            Text(
                                                "Search projects, prompts, tags...",
                                                color = OnSurfaceVariant,
                                                fontSize = 13.sp
                                            )
                                        },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = Color.Transparent,
                                            unfocusedBorderColor = Color.Transparent,
                                            focusedTextColor = OnSurface,
                                            unfocusedTextColor = OnSurface
                                        ),
                                        modifier = Modifier
                                            .weight(1f)
                                            .testTag("search_projects_input"),
                                        singleLine = true
                                    )
                                    IconButton(
                                        onClick = { viewModel.showToast("Filter settings opened") },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Tune,
                                            contentDescription = "Filter",
                                            tint = OnSurfaceVariant,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }

                            // Horizontal Filter Chips
                            val filterOptions = listOf(
                                "All Videos",
                                "Completed",
                                "Drafts",
                                "In Progress",
                                "9:16 Shorts",
                                "16:9 Landscape"
                            )

                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp),
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(filterOptions) { filter ->
                                    val isSelected = filter == activeFilter
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(20.dp))
                                            .background(if (isSelected) PrimaryContainer else SurfaceContainerHigh)
                                            .clickable { viewModel.setActiveFilter(filter) }
                                            .padding(horizontal = 12.dp, vertical = 6.dp)
                                            .testTag("filter_chip_$filter")
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                                        ) {
                                            if (isSelected) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(6.dp)
                                                        .clip(CircleShape)
                                                        .background(Tertiary)
                                                )
                                            }
                                            Text(
                                                text = filter,
                                                color = if (isSelected) OnPrimaryContainer else OnSurfaceVariant,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Recent Projects Header
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Recent Projects",
                                    color = OnSurface,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(SurfaceContainerHighest)
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "${filteredProjects.size} active",
                                        color = OnSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable {
                                    viewModel.showToast("Viewing all archived projects")
                                }
                            ) {
                                Text(
                                    text = "View archive",
                                    color = Primary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = Primary,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }

                    // Project Cards
                    items(filteredProjects) { project ->
                        ProjectCard(
                            project = project,
                            onCardClick = {
                                if (project.status == ProjectStatus.COMPLETED || project.status == ProjectStatus.READY) {
                                    onNavigateToExport()
                                } else {
                                    onNavigateToTimeline()
                                }
                            },
                            onEditClick = { onNavigateToTimeline() },
                            onShareClick = { viewModel.showToast("Project share link ready") },
                            onCopyClick = { viewModel.showToast("Prompt copied to clipboard") },
                            onExportClick = { onNavigateToExport() },
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                        )
                    }

                    // Creator Tip Box
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(SurfaceContainerLow)
                                .padding(14.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .clip(CircleShape)
                                        .background(SecondaryContainer.copy(alpha = 0.5f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lightbulb,
                                        contentDescription = null,
                                        tint = Secondary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = "Pro Creator Tip",
                                        color = OnSurface,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "Add camera speed keywords like 'slow pan' or 'dolly zoom' for 30% smoother motion transitions.",
                                        color = OnSurfaceVariant,
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }

                } else {
                    // --- STUDIO SHOWCASE LANDING SECTION (Image 3) ---

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Model Live Pill
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(SurfaceContainerHigh)
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    PulsingDot(color = Tertiary)
                                    Text(
                                        text = "V2.5 MODEL LIVE",
                                        color = Tertiary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(text = "•", color = OnSurfaceVariant, fontSize = 11.sp)
                                    Text(
                                        text = "Realtime Neural Engine",
                                        color = OnSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            Text(
                                text = "Turn Your Ideas Into Videos with AI",
                                color = OnSurface,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                modifier = Modifier.padding(top = 12.dp)
                            )

                            Text(
                                text = "From a single text prompt to complete scene-by-scene script, 4K visuals, studio voiceover, and soundtrack in seconds.",
                                color = OnSurfaceVariant,
                                fontSize = 13.sp,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                lineHeight = 18.sp,
                                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                            )

                            // Interactive Console Box
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLow),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.padding(14.dp),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(SurfaceContainer)
                                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.AutoAwesome,
                                                    contentDescription = null,
                                                    tint = Tertiary,
                                                    modifier = Modifier.size(12.dp)
                                                )
                                                Text(
                                                    "Universal Engine",
                                                    color = OnSurface,
                                                    fontSize = 11.sp
                                                )
                                            }
                                        }

                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(SurfaceContainer)
                                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                        ) {
                                            Text(
                                                "60s Auto",
                                                color = OnSurfaceVariant,
                                                fontSize = 11.sp
                                            )
                                        }
                                    }

                                    OutlinedTextField(
                                        value = fastPrompt,
                                        onValueChange = { fastPrompt = it },
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedContainerColor = SurfaceContainer,
                                            unfocusedContainerColor = SurfaceContainer,
                                            focusedBorderColor = Primary.copy(alpha = 0.5f),
                                            unfocusedBorderColor = Color.Transparent,
                                            focusedTextColor = OnSurface,
                                            unfocusedTextColor = OnSurface
                                        ),
                                        shape = RoundedCornerShape(10.dp),
                                        minLines = 3
                                    )

                                    // Quick tags
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        TagPill("Cinematic 4K")
                                        TagPill("Warm British Narrator")
                                        TagPill("Upbeat Synth")
                                    }

                                    // Big Generate Video Button
                                    Button(
                                        onClick = {
                                            viewModel.setPrompt(fastPrompt)
                                            viewModel.startGeneration(fastPrompt)
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = PrimaryContainer
                                        ),
                                        shape = RoundedCornerShape(24.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(48.dp)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.AutoAwesome,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(18.dp)
                                            )
                                            Text(
                                                "Generate Video",
                                                color = Color.White,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            // Social Proof Metrics
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = SurfaceContainerHigh),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(14.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceAround
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                "2.4M+",
                                                color = Primary,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                "Videos Made",
                                                color = OnSurfaceVariant,
                                                fontSize = 11.sp
                                            )
                                        }
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                "99.4%",
                                                color = Tertiary,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                "CSAT Score",
                                                color = OnSurfaceVariant,
                                                fontSize = 11.sp
                                            )
                                        }
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                "45k+",
                                                color = Secondary,
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                "Educators",
                                                color = OnSurfaceVariant,
                                                fontSize = 11.sp
                                            )
                                        }
                                    }
                                    Text(
                                        "Trusted by top teams worldwide",
                                        color = OnSurfaceVariant,
                                        fontSize = 11.sp,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )
                                }
                            }

                            // Feature Showcases
                            ShowcaseFeatureCard(
                                title = "Autonomous Storyboarding",
                                description = "Generates shot breakdown, voiceover script & prompts. Instantly slices your concepts into pacing-perfect video scenes.",
                                badge = "Auto-Director",
                                imageUrl = Constants.FEATURE_STORYBOARDING,
                                tags = listOf("4 Keyframe Scenes", "142 Words Script")
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            ShowcaseFeatureCard(
                                title = "Studio-Grade Voiceover",
                                description = "Multi-lingual neural voices with emotion & pacing. Natural pauses, conversational cadence, and zero robotic monotone.",
                                badge = "30+ Languages",
                                imageUrl = Constants.FEATURE_VOICEOVER,
                                tags = listOf("48kHz Neural Audio", "Empathetic Tone")
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            ShowcaseFeatureCard(
                                title = "Multi-Track Sync",
                                description = "Auto-timed background audio, dynamic subtitles & transitions. Perfectly aligned beat-drops with subtitle word highlighting.",
                                badge = "Harmonic Timing",
                                imageUrl = Constants.FEATURE_MULTITRACK,
                                tags = listOf("Karaoke Captions", "Ducked Audio")
                            )
                        }
                    }
                }
            }
        }

        // Floating Action Button (+ Create New)
        FloatingActionButton(
            onClick = onNavigateToStudio,
            containerColor = PrimaryContainer,
            contentColor = Color.White,
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 84.dp)
                .testTag("fab_create_new")
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Create New",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    unit: String? = null,
    subValue: String,
    subColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainerHigh),
        modifier = Modifier.width(136.dp)
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
                Text(text = title, color = OnSurfaceVariant, fontSize = 11.sp)
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(16.dp)
                )
            }
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = value,
                    color = OnSurface,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                if (unit != null) {
                    Text(
                        text = " $unit",
                        color = OnSurfaceVariant,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
            }
            Text(
                text = subValue,
                color = subColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ProjectCard(
    project: ProjectItem,
    onCardClick: () -> Unit,
    onEditClick: () -> Unit,
    onShareClick: () -> Unit,
    onCopyClick: () -> Unit,
    onExportClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceContainer),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCardClick() }
            .testTag("project_card_${project.id}")
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Thumbnail with aspect badge & duration
            Box(
                modifier = Modifier
                    .width(105.dp)
                    .height(115.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SurfaceContainerLowest)
            ) {
                AsyncImage(
                    model = project.imageUrl,
                    contentDescription = project.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Top aspect badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(6.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Black.copy(alpha = 0.7f))
                        .padding(horizontal = 4.dp, vertical = 1.dp)
                ) {
                    Text(
                        text = project.aspectRatio,
                        color = Tertiary,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Center Play Icon or Rendering State
                if (project.status == ProjectStatus.RENDERING) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Sync,
                            contentDescription = "Rendering",
                            tint = Tertiary,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = project.activeSceneText ?: "Rendering",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(PrimaryContainer.copy(alpha = 0.85f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                // Duration badge
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(6.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Black.copy(alpha = 0.75f))
                        .padding(horizontal = 4.dp, vertical = 1.dp)
                ) {
                    val durMinutes = project.durationSeconds / 60
                    val durSeconds = project.durationSeconds % 60
                    Text(
                        text = String.format("%02d:%02d", durMinutes, durSeconds),
                        color = OnSurface,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Project Details
            Column(
                modifier = Modifier
                    .weight(1f)
                    .height(115.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Status pill
                        StatusBadge(project.status)
                        Text(
                            text = project.timeAgo,
                            color = OnSurfaceVariant,
                            fontSize = 11.sp
                        )
                    }

                    Text(
                        text = project.title,
                        color = OnSurface,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Text(
                        text = "${project.durationSeconds}s • ${project.resolution}",
                        color = OnSurfaceVariant,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                // Bottom actions row or progress bar
                if (project.status == ProjectStatus.RENDERING) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        LinearProgressIndicator(
                            progress = { project.progressPercent / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp)),
                            color = Tertiary,
                            trackColor = SurfaceContainerHighest
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = project.subtitleStatus ?: "Processing...",
                                color = OnSurfaceVariant,
                                fontSize = 10.sp
                            )
                            Text(
                                text = "Resume",
                                color = Primary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable { onEditClick() }
                            )
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            MiniActionButton(icon = Icons.Default.Edit, onClick = onEditClick)
                            MiniActionButton(icon = Icons.Default.Share, onClick = onShareClick)
                            MiniActionButton(icon = Icons.Default.ContentCopy, onClick = onCopyClick)
                        }

                        if (project.views != null) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Tertiary,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = project.views,
                                    color = OnSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            }
                        } else {
                            Button(
                                onClick = onExportClick,
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryContainer),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text("Export", color = OnPrimaryContainer, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    Icon(
                                        imageVector = Icons.Default.Download,
                                        contentDescription = null,
                                        tint = OnPrimaryContainer,
                                        modifier = Modifier.size(12.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusBadge(status: ProjectStatus) {
    val (label, bg, fg, hasDot) = when (status) {
        ProjectStatus.COMPLETED -> Quad("Completed", TertiaryContainer.copy(alpha = 0.25f), Tertiary, true)
        ProjectStatus.READY -> Quad("Ready", TertiaryContainer.copy(alpha = 0.25f), Tertiary, true)
        ProjectStatus.RENDERING -> Quad("Rendering", SecondaryContainer.copy(alpha = 0.35f), Secondary, true)
        ProjectStatus.ARCHIVED -> Quad("Archived", SurfaceContainerHighest, OnSurfaceVariant, false)
        ProjectStatus.DRAFT -> Quad("Draft", SurfaceContainerHighest, OnSurfaceVariant, false)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(bg)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            if (hasDot) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(fg)
                )
            }
            Text(text = label, color = fg, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

private data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

@Composable
fun MiniActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(SurfaceContainerHigh)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = OnSurfaceVariant,
            modifier = Modifier.size(14.dp)
        )
    }
}

@Composable
fun TagPill(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceContainerHigh)
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(text = text, color = OnSurfaceVariant, fontSize = 11.sp)
    }
}

@Composable
fun ShowcaseFeatureCard(
    title: String,
    description: String,
    badge: String,
    imageUrl: String,
    tags: List<String>
) {
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
                Text(
                    text = title,
                    color = OnSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainerHigh)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(badge, color = Primary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }

            Text(
                text = description,
                color = OnSurfaceVariant,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SurfaceContainerLowest)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    tags.forEach { tag ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.Black.copy(alpha = 0.75f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(tag, color = OnSurface, fontSize = 10.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PulsingDot(color: Color, modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    Box(
        modifier = modifier
            .size(7.dp)
            .alpha(alpha)
            .clip(CircleShape)
            .background(color)
    )
}
