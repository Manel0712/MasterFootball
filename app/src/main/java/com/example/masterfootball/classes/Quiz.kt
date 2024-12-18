package com.example.masterfootball.classes

import android.graphics.drawable.Drawable
import com.example.masterfootball.Videos
import com.example.masterfootball.R

class Quiz (
    val name: String,
    val unlocked: Boolean,
    val logo: Int = R.drawable._753
)

data class Quizs (
    var quizs: List<Quiz>
)