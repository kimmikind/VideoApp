package com.example.videoapp.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApi {
    @GET("search")
    suspend fun searchVideos(
        @Query("key") apiKey: String,
        @Query("q") query: String, // Поисковый запрос
        @Query("part") part: String = "snippet", // Часть ответа (snippet, id и т.д.)
        @Query("maxResults") maxResults: Int? = null, // Максимальное количество результатов
        @Query("type") type: String = "video" // Тип результата (video, playlist и т.д.)
    ): YouTubeSearchResponse

    @GET("videos")
    suspend fun getVideoDetails(
        @Query("key") apiKey: String,
        @Query("id") videoId: String, // ID видео
        @Query("part") part: String = "snippet,contentDetails" // Часть ответа
    ): YouTubeVideoDetailsResponse
}