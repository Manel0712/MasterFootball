package com.example.masterfootball

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.masterfootball.classes.QuestionsTrivial
import com.example.masterfootball.classes.preguntasTrivial
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class trivial : AppCompatActivity() {

    lateinit var pregunta: TextView
    lateinit var opcio1: TextView
    lateinit var opcio2: TextView
    lateinit var opcio3: TextView
    lateinit var numPregunta: TextView
    private var currentIndex = 0
    private var questionsList: MutableList<QuestionsTrivial> = ArrayList()
    var c = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trivial)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Referencia a los TextView
        pregunta = findViewById(R.id.tvPreguntatrivial)
        opcio1 = findViewById(R.id.trivialOpcio1)
        opcio2 = findViewById(R.id.trivialOpcio2)
        opcio3 = findViewById(R.id.trivialOpcio3)
        numPregunta = findViewById(R.id.tvNumTrivial)

        // Cargar preguntas y mostrar la primera
        loadQuestions()
        showQuestion()

        // Configurar eventos de clic para opciones
        opcio1.setOnClickListener { validarPregunta(1) }
        opcio2.setOnClickListener { validarPregunta(2) }
        opcio3.setOnClickListener { validarPregunta(3) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    private fun loadQuestions() {
        val jsonFile = "trivial.json"
        val json: String = this.assets.open(jsonFile).bufferedReader().use { it.readText() }
        val gson = Gson()
        var questionsTrivial: preguntasTrivial = gson.fromJson(json, preguntasTrivial::class.java)
        questionsTrivial.questions.forEach {
            questionsList.add(QuestionsTrivial(it.question, it.option1, it.option2, it.option3, it.answer))
        }
    }

    private fun showQuestion() {
        if (currentIndex < questionsList.size) {
            val currentQuestion = questionsList[currentIndex]
            pregunta.text = currentQuestion.question
            opcio1.text = currentQuestion.option1
            opcio2.text = currentQuestion.option2
            opcio3.text = currentQuestion.option3
            numPregunta.text = (currentIndex + 1).toString()

            // Restablecer colores después de la validación anterior
            resetOptionColors()
        } else {
            // Fin del trivial
            pregunta.text = "¡Has completado el trivial!"
            opcio1.text = ""
            opcio2.text = ""
            opcio3.text = ""
        }
    }

    private fun resetOptionColors() {
        val defaultColor = ContextCompat.getColor(this, android.R.color.white)
        opcio1.setBackgroundColor(defaultColor)
        opcio2.setBackgroundColor(defaultColor)
        opcio3.setBackgroundColor(defaultColor)
    }

    private fun validarPregunta(selectedOption: Int) {
        val currentQuestion = questionsList[currentIndex]
        val correctOption = when (currentQuestion.answer) {
            currentQuestion.option1 -> 1
            currentQuestion.option2 -> 2
            currentQuestion.option3 -> 3
            else -> -1
        }

        if (selectedOption == correctOption) {
            // Respuesta correcta
            setOptionColor(selectedOption, R.color.respuestaCorrecta)
        } else {
            // Respuesta incorrecta
            setOptionColor(selectedOption, R.color.respuestaIncorrecta)
            setOptionColor(correctOption, R.color.respuestaCorrecta)
        }

        // Mostrar la siguiente pregunta después de un breve retraso
        opcio1.postDelayed({
            currentIndex++
            showQuestion()
        }, 1500)
    }

    private fun setOptionColor(option: Int, colorRes: Int) {
        val color = ContextCompat.getColor(this, colorRes)
        when (option) {
            1 -> opcio1.setBackgroundColor(color)
            2 -> opcio2.setBackgroundColor(color)
            3 -> opcio3.setBackgroundColor(color)
        }
    }


}