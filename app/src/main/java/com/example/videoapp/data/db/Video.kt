package com.example.videoapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos")
data class Video(
    @PrimaryKey val id: String,
    val title: String,
    val thumbnailUrl: String,
    val duration: String,
    val videoUrl: String,

)