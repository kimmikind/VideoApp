package com.example.videoapp.data.api

data class YouTubeSearchResponse(
    val items: List<YouTubeVideoItem>
)

data class YouTubeVideoItem(
    val id: YouTubeVideoId,
    val snippet: YouTubeVideoSnippet
)

data class YouTubeVideoId(
    val videoId: String
)

data class YouTubeVideoSnippet(
    val title: String,
    val description: String,
    val thumbnails: YouTubeThumbnails
)

data class YouTubeThumbnails(
    val default: YouTubeThumbnail,
    val medium: YouTubeThumbnail,
    val high: YouTubeThumbnail
)

data class YouTubeThumbnail(
    val url: String
)

data class YouTubeVideoDetailsResponse(
    val items: List<YouTubeVideoDetails>
)

data class YouTubeVideoDetails(
    val id: String,
    val snippet: YouTubeVideoSnippet,
    val contentDetails: YouTubeContentDetails
)

data class YouTubeContentDetails(
    val duration: String
)