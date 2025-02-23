package com.example.videoapp.utils

import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.videoapp.data.db.Video

@Composable
fun VideoItem(video: Video, onClick: () -> Unit) {
    Log.d("VideoItem", "videoUrl: ${video.videoUrl}")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column {
            AsyncImage(
                model = video.thumbnailUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Text(
                text = video.title,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.h6
            )
            Text(
                text = video.duration,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.body2
            )
        }
    }
}