package com.example.videoapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideoDao {
    @Query("SELECT * FROM videos")
    suspend fun getVideos(): List<Video>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<Video>)
}