package com.example.masterfootball.classes

class QuestionsTrivial (
    val question: String,
    val Option1: String,
    val Option2: String,
    val Option3: String,
    val answer: String
)

data class preguntasTrivial (
    var questions: List<QuestionsTrivial>
)