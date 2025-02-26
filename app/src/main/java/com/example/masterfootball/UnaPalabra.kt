package com.example.masterfootball

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.example.masterfootball.classes.Quiz
import com.example.masterfootball.classes.Quizs
import com.example.masterfootball.classes.UnaPalabra
import com.example.masterfootball.classes.UnaPalabraList
import com.example.masterfootball.classes.updatePointsANDMoneys
import com.example.masterfootball.databinding.UnaPalabraBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.io.InputStreamReader

class UnaPalabra : AppCompatActivity() {
    private var questionsList: MutableList<UnaPalabra> = ArrayList()
    private var currentQuestionIndex = 0

    private lateinit var img1: ImageView
    private lateinit var img2: ImageView
    private lateinit var img3: ImageView
    private lateinit var img4: ImageView
    private lateinit var inputResposta: EditText
    private lateinit var btnComprobar: Button
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.una_palabra)  // Asegúrate de que este es el nombre correcto del layout XML

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setDisplayShowTitleEnabled(false)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = getColor(R.color.black)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        // Inicializar vistas
        img1 = findViewById(R.id.img1)
        img2 = findViewById(R.id.img2)
        img3 = findViewById(R.id.img3)
        img4 = findViewById(R.id.img4)
        inputResposta = findViewById(R.id.inputResposta)
        btnComprobar = findViewById(R.id.btnComprobar)
        id = intent.extras!!.getInt("userId")

        // Cargar preguntas y mostrar la primera
        loadQuestions()
        loadQuestion()

        btnComprobar.setOnClickListener {
            checkAnswer()
        }
    }

    private fun loadQuestions() {
        try {
            var json: String = this.assets.open("unaPalabra.json").bufferedReader().use { it.readText() }
            var gson: Gson = Gson()

            var fotos: UnaPalabraList = gson.fromJson(json, UnaPalabraList::class.java)

            fotos.unaPalabraList.forEach{
                questionsList.add(UnaPalabra(it.images, it.answer))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "No se pudo cargar el archivo JSON", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadQuestion() {
        val question = questionsList.random()

        currentQuestionIndex = questionsList.indexOf(question)

        val resourceId = resources.getIdentifier(question.images[0], "drawable", packageName)
        img1.setImageResource(resourceId)

        val resourceId2 = resources.getIdentifier(question.images[1], "drawable", packageName)
        img2.setImageResource(resourceId2)

        val resourceId3 = resources.getIdentifier(question.images[2], "drawable", packageName)
        img3.setImageResource(resourceId3)

        val resourceId4 = resources.getIdentifier(question.images[3], "drawable", packageName)
        img4.setImageResource(resourceId4)
    }

    private fun checkAnswer() {
        val userAnswer = inputResposta.text.toString().trim()
        val correctAnswer = questionsList[currentQuestionIndex].answer

        if (userAnswer.equals(correctAnswer, ignoreCase = true)) {
            Snackbar.make(findViewById<View>(android.R.id.content),"Respuesta correcta", Snackbar.LENGTH_LONG)
                .show()
            lifecycleScope.launch {
                var update = updatePointsANDMoneys()
                update.updatePointsANDMoneys(5, 5, 2, id)
            }
        } else {
            Snackbar.make(findViewById<View>(android.R.id.content),"Respuesta incorrecta. Inténtalo de nuevo.", Snackbar.LENGTH_LONG)
                .show()
        }
    }
}