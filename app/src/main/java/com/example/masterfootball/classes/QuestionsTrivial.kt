package com.example.masterfootball.classes

class QuestionsTrivial (
    val question: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val answer: String
)

data class preguntasTrivial (
    var questions: List<QuestionsTrivial>
)