package com.example.videoapp.data.repository

import android.util.Log


import com.example.videoapp.data.api.YouTubeApi
import com.example.videoapp.data.api.YouTubeVideoItem
import com.example.videoapp.data.db.Video
import com.example.videoapp.data.db.VideoDao
import com.example.videoapp.model.Result
import com.google.android.exoplayer2.Timeline

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

import java.io.IOException
import java.time.Duration
import javax.inject.Inject
import kotlin.coroutines.resume

class VideoRepository @Inject constructor(
    private val youtubeApi: YouTubeApi,
    private val videoDao: VideoDao
) {
    suspend fun getCachedVideos(): List<Video> {
        return videoDao.getVideos()
    }

    suspend fun getVideos(
        apiKey: String,
        query: String,
        maxResults: Int? = null
    ): Result<List<Video>> {
        return try {
            Log.d("VideoRepository", " Отправляем запрос в YouTube API...")

            // Запрос на получение списка видео
            val response = youtubeApi.searchVideos(
                apiKey = apiKey,
                query = query,
                maxResults = maxResults
            )
            Log.d("VideoRepository", " Ответ API получен: ${response.items.size} видео")

            // Преобразуем ответ API в список Video
            val videos = response.items.map { video ->
                // Получаем длительность видео
                val duration = getVideoDuration(apiKey, video.id.videoId)
                video.toVideo(duration)
            }

            // Логируем ссылки на видео
            for (video in videos) {
                Log.d("rrr", "Ответ API получен: ${video.id} ${video.duration} видео")
            }

            // Сохраняем видео в базу данных
            videoDao.insertVideos(videos)
            Log.d("VideoRepository", " Видео сохранены в БД")

            Result.Success(videos)
        } catch (e: Exception) {
            Log.e("VideoRepository", " Ошибка при запросе API", e)
            val cachedVideos = videoDao.getVideos()
            if (cachedVideos.isNotEmpty()) {
                Log.w("VideoRepository", "⚠ Ошибка, но возвращаем кешированные видео (${cachedVideos.size})")
                Result.Success(cachedVideos)
            } else {
                Log.e("VideoRepository", " Ошибка критическая, данных нет")
                Result.Error(e)
            }
        }
    }

    private suspend fun getVideoDuration(apiKey: String, videoId: String): String? {
        return try {
            val response = youtubeApi.getVideoDetails(apiKey, videoId)
            response.items.firstOrNull()?.contentDetails?.duration?.let { parseDuration(it) }
        } catch (e: Exception) {
            Log.e("VideoRepository", "Ошибка при получении длительности видео", e)
            null
        }
    }

    private fun parseDuration(duration: String): String {
        // Преобразуем длительность из формата ISO 8601 в читаемый формат
        return try {
            // Разбираем длительность из формата ISO 8601
            val parsedDuration = Duration.parse(duration)

            // Преобразуем длительность в читаемый формат (часы:минуты:секунды)
            val hours = parsedDuration.toHours()
            val minutes = parsedDuration.toMinutes() % 60
            val seconds = parsedDuration.seconds % 60

            // Форматируем результат
            String.format("%02d:%02d:%02d", hours, minutes, seconds)
        } catch (e: Exception) {
            duration
        }
    }

    private fun YouTubeVideoItem.toVideo(duration: String?): Video {
        return Video(
            id = id.videoId, // Уникальный ID видео
            title = snippet.title,
            thumbnailUrl = snippet.thumbnails.high.url, // Используем высококачественную миниатюру
            duration = duration ?: "0 сек", // Используем переданную длительность
            videoUrl = "" // Прямая ссылка на видео не требуется, так как используем YouTubePlayer
        )
    }
}