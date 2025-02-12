package com.example.masterfootball.classes

class frase (
    val frase: String,
    val opcion_1: String,
    val opcion_2: String,
    val respuesta_correcta: String,
)

data class Frases (
    var frases: List<frase>
)