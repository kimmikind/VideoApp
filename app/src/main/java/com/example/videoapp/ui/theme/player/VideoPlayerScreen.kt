package com.example.videoapp.ui.theme.player

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.util.Log
import android.util.SparseArray
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.example.videoapp.model.VideoPlayerViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun VideoPlayerScreen(videoId: String,  videoTitle: String,
                      videoDuration: String,
                      viewModel: VideoPlayerViewModel = hiltViewModel()) {
    val context = LocalContext.current
    Log.d("VideoPlayerScreen", " Получен id видео: $videoId")
    //видео заглушка
    val exoPlayer = remember { viewModel.getExoPlayer(context) }

    // Загрузите видео при первом запуске
    LaunchedEffect(videoId) {
        val videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4"
        val mediaItem = MediaItem.fromUri(videoUrl)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
    }


    // Освобождаем ресурсы при закрытии экрана
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // Состояние для полноэкранного режима
    var isFullscreen by remember { mutableStateOf(false) }

    // Обработчик поворота экрана
    val orientation = LocalConfiguration.current.orientation
    LaunchedEffect(orientation) {
        isFullscreen = orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = true
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                    Log.d("VideoPlayerScreen", " PlayerView создан и настроен")
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        )

        // Кнопка для перехода в полноэкранный режим
        if (!isFullscreen) {
            IconButton(
                onClick = {
                    // Переключение ориентации экрана
                    val activity = context as Activity
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp)
                    .zIndex(1f) //  кнопка поверх PlayerView
            ) {
                Icon(
                    imageVector = Icons.Filled.Fullscreen,
                    contentDescription = "Полноэкранный режим",
                    tint = Color.White
                )
            }
        }
        // Кнопка для выхода из полноэкранного режима
        if (isFullscreen) {
            IconButton(
                onClick = {
                    // Переключение ориентации экрана в портретный режим
                    val activity = context as Activity
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .zIndex(1f) //  кнопка поверх PlayerView
            ) {
                Icon(
                    imageVector = Icons.Filled.FullscreenExit,
                    contentDescription = "Выйти из полноэкранного режима",
                    tint = Color.White
                )
            }
        }
    }
}
//решение через использование youtube player
/*fun VideoPlayerScreen(
    videoId: String,
    videoTitle: String,
    videoDuration: String
) {
    val context = LocalContext.current
    val youtubePlayerView = remember { YouTubePlayerView(context) }

    // Управление состоянием YouTubePlayer
    DisposableEffect(Unit) {
        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })

        onDispose {
            youtubePlayerView.release()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Воспроизведение видео
        AndroidView(
            factory = { youtubePlayerView },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Название видео
        Text(
            text = videoTitle,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Длительность видео
        Text(
            text = "Длительность: $videoDuration",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.fillMaxWidth()
        )
    }
}*/