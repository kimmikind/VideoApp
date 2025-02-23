package com.example.videoapp.model

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(): ViewModel() {
    private var exoPlayer: ExoPlayer? = null

    fun getExoPlayer(context: Context): ExoPlayer {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(context).build()
        }
        return exoPlayer!!
    }

    override fun onCleared() {
        exoPlayer?.release()
        exoPlayer = null
    }
}