package com.example.masterfootball

import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.masterfootball.classes.QuestionQuiz
import com.example.masterfootball.classes.preguntasTrivial
import com.google.gson.Gson

class trivial : AppCompatActivity() {

    lateinit var pregunta: TextView
    lateinit var opcio1: TextView
    lateinit var opcio2: TextView
    lateinit var opcio3: TextView
    lateinit var numPregunta: TextView

    private var currentIndex = 0
    private var questionsList = mutableListOf<QuestionQuiz>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.trivial)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        pregunta = findViewById(R.id.tvPreguntatrivial)
        opcio1 = findViewById(R.id.trivialOpcio1)
        opcio2 = findViewById(R.id.trivialOpcio2)
        opcio3 = findViewById(R.id.trivialOpcio3)
        numPregunta = findViewById(R.id.tvNumTrivial)

        loadQuestions()
        showQuestion()

        // Configurar eventos de clic para opciones
        opcio1.setOnClickListener { nextQuestion() }
        opcio2.setOnClickListener { nextQuestion() }
        opcio3.setOnClickListener { nextQuestion() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    private fun loadQuestions() {
        val jsonFile = "trivial.json"
        val json: String? = this.assets.open(jsonFile).bufferedReader().use { it.readText() }
        val gson = Gson()
        val newTrivial: preguntasTrivial = gson.fromJson(json, preguntasTrivial::class.java)
        questionsList = newTrivial.questions.toMutableList()
    }

    private fun showQuestion() {
        if (currentIndex < questionsList.size) {
            val currentQuestion = questionsList[currentIndex]
            pregunta.text = currentQuestion.question
            opcio1.text = currentQuestion.Option1
            opcio2.text = currentQuestion.Option2
            opcio3.text = currentQuestion.Option3
            numPregunta.text = (currentIndex + 1).toString()
        } else {
            // Fin del trivial, puedes mostrar un mensaje o terminar la actividad
            pregunta.text = "Has completado el trivial!"
            opcio1.text = ""
            opcio2.text = ""
            opcio3.text = ""
        }
    }

    private fun nextQuestion() {
        currentIndex++
        showQuestion()
    }
}