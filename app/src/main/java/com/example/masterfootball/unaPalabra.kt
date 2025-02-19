package com.example.masterfootball

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.masterfootball.classes.unaPalabra
import com.google.gson.Gson
import java.io.InputStreamReader

class unaPalabra : AppCompatActivity() {
    var questionsList: MutableList<unaPalabra> = ArrayList()
    var currentQuestionIndex = 0
    lateinit var img1: ImageView
    lateinit var img2: ImageView
    lateinit var img3: ImageView
    lateinit var img4: ImageView
    lateinit var inputResposta: EditText
    lateinit var btnComprobar: Button
    lateinit var gameLayout: ConstraintLayout
    lateinit var resultLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_una_palabra)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        img1 = findViewById(R.id.img1)
        img2 = findViewById(R.id.img2)
        img3 = findViewById(R.id.img3)
        img4 = findViewById(R.id.img4)
        inputResposta = findViewById(R.id.inputResposta)
        btnComprobar = findViewById(R.id.btnComprobar)
        gameLayout = findViewById(R.id.gameLayout)
        resultLayout = findViewById(R.id.resultLayout)

        loadQuestions()
        loadQuestion(currentQuestionIndex)

        btnComprobar.setOnClickListener {
            checkAnswer()
        }
    }

    private fun loadQuestions() {
        val jsonStream = assets.open("unaPalabra.json")
        val jsonReader = InputStreamReader(jsonStream)
        val gson = Gson()
        val questionsData = gson.fromJson(jsonReader, Array<unaPalabra>::class.java).toList()
        questionsList.addAll(questionsData)
    }

    private fun loadQuestion(index: Int) {
        val question = questionsList[index]
        Glide.with(this).load(question.images[0]).into(img1)
        Glide.with(this).load(question.images[1]).into(img2)
        Glide.with(this).load(question.images[2]).into(img3)
        Glide.with(this).load(question.images[3]).into(img4)
    }

    private fun checkAnswer() {
        val userAnswer = inputResposta.text.toString().trim()
        val correctAnswer = questionsList[currentQuestionIndex].answer

        if (userAnswer.equals(correctAnswer, ignoreCase = true)) {
            currentQuestionIndex++
            if (currentQuestionIndex < questionsList.size) {
                loadQuestion(currentQuestionIndex)
                inputResposta.text.clear()
            } else {
                gameLayout.visibility = View.GONE
                resultLayout.visibility = View.VISIBLE
            }
        } else {
            inputResposta.error = "Respuesta incorrecta. Inténtalo de nuevo."
        }
    }
}
