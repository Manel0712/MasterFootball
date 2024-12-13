package com.example.masterfootball.classes

import com.example.masterfootball.Videos

class Video (
    val name: String,
    val url: String,
    val unlocked: Boolean
)

data class Videos (
    var videos: List<Video>
)