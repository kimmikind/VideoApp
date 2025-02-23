package com.example.videoapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videoapp.data.db.Video
import com.example.videoapp.data.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val repository: VideoRepository
) : ViewModel() {

    private val _videos = MutableLiveData<Result<List<Video>>>()
    val videos: LiveData<Result<List<Video>>> get() = _videos

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    fun loadVideos(apiKey: String, query: String, maxResults: Int? = null) {
        viewModelScope.launch {
            Log.d("VideoViewModel", "Начинаем загрузку видео...")
            _isRefreshing.postValue(true)

            // Сначала пытаемся получить данные из базы данных
            val cachedVideos = repository.getCachedVideos()
            if (cachedVideos.isNotEmpty()) {
                Log.d("VideoViewModel", " Видео из кеша: ${cachedVideos.size}")
                _videos.postValue(Result.Success(cachedVideos))
            }

            // Затем загружаем данные из API
            val result = repository.getVideos(apiKey, query, maxResults)
            when (result) {
                is Result.Success -> Log.d("VideoViewModel", " Видео загружены: ${result.data.size}")
                is Result.Error -> Log.e("VideoViewModel", " Ошибка загрузки видео", result.exception)
                Result.Loading -> Log.d("VideoViewModel", "Идет загрузка видео")
            }
            _videos.postValue(result)
            Log.d("VideoViewModel", " _videos обновлено")
            _isRefreshing.postValue(false)
        }
    }
}