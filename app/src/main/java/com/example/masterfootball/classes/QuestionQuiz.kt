package com.example.masterfootball.classes

class QuestionQuiz (
    val question: String,
    val correctOption: String,
    val Option1: String,
    val Option2: String,
    val Option3: String,
    val Option4: String,
    var selectedOption: String,
)

data class Questions (
    var questions: List<QuestionQuiz>
)