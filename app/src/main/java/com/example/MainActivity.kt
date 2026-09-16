package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.FloatingBottomNavBar
import com.example.ui.screens.ActiveGenerationScreen
import com.example.ui.screens.ExportScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.StoryboardScreen
import com.example.ui.screens.StudioScreen
import com.example.ui.screens.TimelineScreen
import com.example.ui.theme.OnSurface
import com.example.ui.theme.Prompt2VideoTheme
import com.example.ui.theme.Surface
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.Tertiary
import com.example.ui.viewmodel.AppNavigationTab
import com.example.ui.viewmodel.VideoStudioViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Prompt2VideoTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(
    viewModel: VideoStudioViewModel = viewModel()
) {
    val currentTab by viewModel.currentTab.collectAsState()
    val isShowingGeneration by viewModel.isShowingGenerationScreen.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("app_main_scaffold"),
        containerColor = Surface
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Main Screen Content or Active Generation Screen
            if (isShowingGeneration) {
                ActiveGenerationScreen(
                    viewModel = viewModel,
                    onBackClick = { viewModel.dismissGenerationScreen() },
                    onViewStoryboard = {
                        viewModel.dismissGenerationScreen()
                        viewModel.selectTab(AppNavigationTab.STORYBOARD)
                    }
                )
            } else {
                AnimatedContent(
                    targetState = currentTab,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "tab_transition"
                ) { targetTab ->
                    when (targetTab) {
                        AppNavigationTab.HOME -> {
                            HomeScreen(
                                viewModel = viewModel,
                                onNavigateToStudio = { viewModel.selectTab(AppNavigationTab.STUDIO) },
                                onNavigateToTimeline = { viewModel.selectTab(AppNavigationTab.TIMELINE) },
                                onNavigateToExport = { viewModel.selectTab(AppNavigationTab.EXPORTS) }
                            )
                        }

                        AppNavigationTab.STUDIO -> {
                            StudioScreen(
                                viewModel = viewModel,
                                onBackClick = { viewModel.selectTab(AppNavigationTab.HOME) },
                                onStartGeneration = { viewModel.startGeneration() }
                            )
                        }

                        AppNavigationTab.STORYBOARD -> {
                            StoryboardScreen(
                                viewModel = viewModel,
                                onBackClick = { viewModel.selectTab(AppNavigationTab.HOME) },
                                onOpenTimeline = { viewModel.selectTab(AppNavigationTab.TIMELINE) }
                            )
                        }

                        AppNavigationTab.TIMELINE -> {
                            TimelineScreen(
                                viewModel = viewModel,
                                onBackClick = { viewModel.selectTab(AppNavigationTab.STORYBOARD) },
                                onExportClick = { viewModel.selectTab(AppNavigationTab.EXPORTS) }
                            )
                        }

                        AppNavigationTab.EXPORTS -> {
                            ExportScreen(
                                viewModel = viewModel,
                                onBackClick = { viewModel.selectTab(AppNavigationTab.TIMELINE) },
                                onEditAgain = { viewModel.selectTab(AppNavigationTab.TIMELINE) },
                                onCreateNew = { viewModel.selectTab(AppNavigationTab.STUDIO) }
                            )
                        }
                    }
                }

                // Floating Bottom Nav Bar
                FloatingBottomNavBar(
                    currentTab = currentTab,
                    onTabSelected = { viewModel.selectTab(it) },
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }

            // Toast / Notification Pill
            AnimatedVisibility(
                visible = toastMessage != null,
                enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .statusBarsPadding()
                    .padding(top = 12.dp)
            ) {
                toastMessage?.let { msg ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(SurfaceContainerHighest.copy(alpha = 0.95f))
                            .border(1.dp, Tertiary.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                            .clickable { viewModel.clearToast() }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = msg,
                            color = OnSurface,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
