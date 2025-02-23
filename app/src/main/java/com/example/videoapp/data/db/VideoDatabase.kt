package com.example.videoapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Video::class], version = 2, exportSchema = false)
abstract class VideoDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
}