package com.example.videoapp.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.videoapp.ui.theme.list.VideoListScreen
import com.example.videoapp.ui.theme.player.VideoPlayerScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "videoList") {
        composable("videoList") {
            VideoListScreen(navController = navController)
        }
        composable(
            route = "videoPlayer/{videoId}/{title}/{duration}", // Маршрут с параметрами
            arguments = listOf(
                navArgument("videoId") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType },
                navArgument("duration") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val videoId = backStackEntry.arguments?.getString("videoId") ?: ""
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val duration = backStackEntry.arguments?.getString("duration") ?: ""

            Log.d("VideoPlayerScreen", "✅ Получен id видео: $videoId, title: $title, duration: $duration")

            VideoPlayerScreen(
                videoId = videoId,
                videoTitle = title,
                videoDuration = duration
            )
        }
    }
}