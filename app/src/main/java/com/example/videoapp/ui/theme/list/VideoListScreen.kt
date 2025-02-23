package com.example.videoapp.ui.theme.list

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.videoapp.model.VideoViewModel
import com.example.videoapp.utils.SwipeRefresh
import com.example.videoapp.utils.VideoItem
import com.example.videoapp.model.Result

@Composable
fun VideoListScreen(viewModel: VideoViewModel = hiltViewModel(), navController: NavController) {
    Log.d("VideoListScreen", " Экран VideoListScreen создан")
    val videos by viewModel.videos.observeAsState(Result.Loading)
    val isRefreshing by viewModel.isRefreshing.observeAsState(false)

    val apiKey = "AIzaSyA_PpzXWYQ9PnC6M2qhL7ongj8OZw1o7EM"
    val query = "subaru car"
    // Загружаем видео при первом запуске экрана
    LaunchedEffect(Unit) {
        viewModel.loadVideos(
            apiKey = apiKey, // Укажите ваш API ключ YouTube
            query = query, // Поисковый запрос
            maxResults = 10 // Количество видео
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Video Player",
                        modifier = Modifier.padding(top = 20.dp) // Отступ для текста
                    )
                },
                backgroundColor = MaterialTheme.colors.primarySurface, // Цвет фона в соответствии с темой
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (videos) {
                is Result.Success -> {
                    val videoList = (videos as Result.Success).data
                    SwipeRefresh(
                        isRefreshing = isRefreshing,
                        onRefresh = { viewModel.loadVideos(
                            apiKey = apiKey,
                            query = query,
                            maxResults = 10
                        ) }
                    ) {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(videoList) { video ->
                                VideoItem(video = video, onClick = {
                                    Log.d("VideoListScreen", " Переход на VideoPlayerScreen с videoId: ${video.id}")
                                    navController.navigate("videoPlayer/${video.id}/${video.title}/${video.duration}") // Передаем videoId, title и duration
                                })
                            }
                        }
                    }
                }
                is Result.Error -> {
                    Text(
                        text = "Ошибка загрузки данных",
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.Center)
                    )
                }
                Result.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}