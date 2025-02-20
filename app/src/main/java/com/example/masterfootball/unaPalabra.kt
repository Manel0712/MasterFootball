package com.example.masterfootball

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.masterfootball.classes.PalabraJuego
import com.example.masterfootball.databinding.UnaPalabraBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

class unaPalabra : AppCompatActivity() {
    private var questionsList: MutableList<UnaPalabra> = mutableListOf()
    private var currentQuestionIndex = 0

    private lateinit var img1: ImageView
    private lateinit var img2: ImageView
    private lateinit var img3: ImageView
    private lateinit var img4: ImageView
    private lateinit var inputResposta: EditText
    private lateinit var btnComprobar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.una_palabra)  // Asegúrate de que este es el nombre correcto del layout XML

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar vistas
        img1 = findViewById(R.id.img1)
        img2 = findViewById(R.id.img2)
        img3 = findViewById(R.id.img3)
        img4 = findViewById(R.id.img4)
        inputResposta = findViewById(R.id.inputResposta)
        btnComprobar = findViewById(R.id.btnComprobar)

        // Cargar preguntas y mostrar la primera
        loadQuestions()
        if (questionsList.isNotEmpty()) {
            loadQuestion(currentQuestionIndex)
        } else {
            Toast.makeText(this, "Error al cargar las preguntas", Toast.LENGTH_LONG).show()
        }

        btnComprobar.setOnClickListener {
            checkAnswer()
        }
    }

    private fun loadQuestions() {
        try {
            val jsonStream = assets.open("unaPalabra.json")
            val jsonReader = InputStreamReader(jsonStream)
            val gson = Gson()
            val type = object : TypeToken<List<PalabraJuego>>() {}.type
            questionsList = gson.fromJson(jsonReader, type) ?: mutableListOf()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "No se pudo cargar el archivo JSON", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadQuestion(index: Int) {
        val question = questionsList[index]

        Glide.with(this).load(question.images[0]).into(img1)
        Glide.with(this).load(question.images[1]).into(img2)
        Glide.with(this).load(question.images[2]).into(img3)
        Glide.with(this).load(question.images[3]).into(img4)

        // Limpiar la respuesta anterior
        inputResposta.text.clear()
    }

    private fun checkAnswer() {
        val userAnswer = inputResposta.text.toString().trim()
        val correctAnswer = questionsList[currentQuestionIndex].answer

        if (userAnswer.equals(correctAnswer, ignoreCase = true)) {
            currentQuestionIndex++
            if (currentQuestionIndex < questionsList.size) {
                loadQuestion(currentQuestionIndex)
            } else {
                val intent = Intent(this, Juegos::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            inputResposta.error = "Respuesta incorrecta. Inténtalo de nuevo."
        }
    }
}
