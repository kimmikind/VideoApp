package com.example.videoapp.di



import com.example.videoapp.data.api.RetrofitClient
import com.example.videoapp.data.api.YouTubeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideVideoApi(): YouTubeApi {
        return RetrofitClient.instance
    }
}