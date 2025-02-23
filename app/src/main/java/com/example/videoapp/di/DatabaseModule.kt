package com.example.videoapp.di

import android.content.Context
import androidx.room.Room
import com.example.videoapp.data.db.VideoDao
import com.example.videoapp.data.db.VideoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideVideoDatabase(@ApplicationContext context: Context): VideoDatabase {
        return Room.databaseBuilder(
            context,
            VideoDatabase::class.java,
            "video_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideVideoDao(database: VideoDatabase): VideoDao {
        return database.videoDao()
    }
}