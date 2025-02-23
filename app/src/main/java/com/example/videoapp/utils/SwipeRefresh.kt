package com.example.videoapp.utils

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun SwipeRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit
) {
    val refreshDistance = 100.dp
    val refreshState = remember { mutableStateOf(false) }
    val dragState = remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectVerticalDragGestures { change, dragAmount ->
                    if (dragAmount > 0 && !isRefreshing) {
                        dragState.value += dragAmount
                        if (dragState.value > refreshDistance.toPx()) {
                            refreshState.value = true
                            onRefresh()
                        }
                    }
                }
            }
    ) {
        content()

        if (isRefreshing || refreshState.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
            )
        }
    }
}