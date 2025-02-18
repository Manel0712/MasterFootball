package com.example.masterfootball

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.masterfootball.classes.QuestionsTrivial
import com.google.gson.Gson

class unaPalabra: AppCompatActivity() {

    lateinit var img1: ImageView
    lateinit var img2: ImageView
    lateinit var img3: ImageView
    lateinit var img4: ImageView
    lateinit var comprobar: Button
    lateinit var resposta: TextView
    private var arrayImatges: MutableList<QuestionsTrivial> = ArrayList()
    private var currentQuestionIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.una_palabra)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = getColor(R.color.black)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        img1 = findViewById(R.id.img1)
        img2 = findViewById(R.id.img2)
        img3 = findViewById(R.id.img3)
        img4 = findViewById(R.id.img4)
        resposta = findViewById(R.id.inputResposta)
        comprobar = findViewById(R.id.btnComprobar)

        // Cargar preguntas desde JSON (puedes cargarlo desde un archivo o una API)
        loadQuestions()

        // Cargar la primera pregunta
        loadQuestion(currentQuestionIndex)

        findViewById<Button>(R.id.btnComprobar).setOnClickListener {
            checkAnswer()
        }
    }

    // Función para cargar las preguntas desde JSON
    private fun loadQuestions() {
        val json = """
            {
                "questions": [
                    {
                        "images": ["trivial1_1", "trivial1_2", "trivial1_3", "trivial1_4"],
                        "answer": "porteria"
                    },
                    {
                        "images": ["luna1", "luna2", "luna3", "luna4"],
                        "answer": "luna"
                    },
                    {
                        "images": ["mar1", "mar2", "mar3", "mar4"],
                        "answer": "mar"
                    },
                    {
                        "images": ["fuego1", "fuego2", "fuego3", "fuego4"],
                        "answer": "fuego"
                    },
                    {
                        "images": ["montaña1", "montaña2", "montaña3", "montaña4"],
                        "answer": "montaña"
                    }
                ]
            }
        """.trimIndent()

        val gson = Gson()
        val questions = gson.fromJson(json, QuestionsResponse::class.java)
        questionsList.addAll(questions.questions)
    }

    // Función para cargar una pregunta y sus imágenes
    private fun loadQuestion(index: Int) {
        val question = questionsList[index]
        Glide.with(this).load("url_to_image/${question.images[0]}").into(img1)
        Glide.with(this).load("url_to_image/${question.images[1]}").into(img2)
        Glide.with(this).load("url_to_image/${question.images[2]}").into(img3)
        Glide.with(this).load("url_to_image/${question.images[3]}").into(img4)
    }

    // Función para comprobar la respuesta
    private fun checkAnswer() {
        val userAnswer = resposta.text.toString().trim()
        val correctAnswer = questionsList[currentQuestionIndex].answer

        if (userAnswer.equals(correctAnswer, ignoreCase = true)) {
            currentQuestionIndex++
            if (currentQuestionIndex < questionsList.size) {
                loadQuestion(currentQuestionIndex)
                resposta.text = ""
            } else {
                // Final del juego, mostrar mensaje de victoria
            }
        } else {
            // Respuesta incorrecta, mostrar mensaje
        }
    }




}