package com.example.masterfootball.classes

import com.example.masterfootball.R
import com.example.masterfootball.Videos

class Video (
    val name: String,
    val url: String,
    val unlocked: Boolean,
    val logo: Int = R.drawable.sin_t_tulo
)

data class Videos (
    var videos: List<Video>
)