package com.example.masterfootball.classes

import com.example.masterfootball.R

class Quiz (
    val name: String,
    var unlocked: Boolean,
    val logo: Int = R.drawable._753
)

data class Quizs (
    var quizs: List<Quiz>
)