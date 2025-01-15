package com.example.masterfootball

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masterfootball.adapters.quizAdapter
import com.example.masterfootball.adapters.quizAnswersAdapter
import com.example.masterfootball.adapters.videosAdapter
import com.example.masterfootball.classes.QuestionQuiz
import com.example.masterfootball.classes.Questions
import com.example.masterfootball.classes.Video
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class quiz1 : AppCompatActivity() {
    lateinit var myRecyclerView : RecyclerView
    var questionsList : MutableList<QuestionQuiz> = ArrayList()
    var c = 0
    var number = 0
    lateinit var question: TextView
    lateinit var option1: TextView
    lateinit var option2: TextView
    lateinit var option3: TextView
    lateinit var option4: TextView
    lateinit var questionsLayout: ConstraintLayout
    lateinit var reviewLayout: ConstraintLayout
    val mAdapter : quizAnswersAdapter = quizAnswersAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz_play)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        number = intent.extras!!.getInt("number")
        question = findViewById(R.id.question)
        option1 = findViewById(R.id.opcio1)
        option2 = findViewById(R.id.opcio2)
        option3 = findViewById(R.id.opcio3)
        option4 = findViewById(R.id.opcio4)
        questionsLayout = findViewById(R.id.questionsLayaout)
        reviewLayout = findViewById(R.id.reviewLayaout)
        loadSimple()
        quizCreate()
    }

    private fun loadSimple() {
        var jsonFile = "quiz" + number + ".json"
        var json: String = this.assets.open(jsonFile).bufferedReader().use { it.readText() }
        var gson: Gson = Gson()

        var newQuestion: Questions = gson.fromJson(json, Questions::class.java)

        newQuestion.questions.forEach(){
            questionsList.add(QuestionQuiz(it.question, it.correctOption, it.Option1, it.Option2, it.Option3, it.Option4, it.selectedOption))
        }
    }

    private fun quizCreate() {
        question.setText(questionsList[c].question)
        option1.setText(questionsList[c].Option1)
        option2.setText(questionsList[c].Option2)
        option3.setText(questionsList[c].Option3)
        option4.setText(questionsList[c].Option4)
    }

    fun option1Click(view: View) {
        questionsList[c].selectedOption = option1.text.toString()
        c++
        if (c<10) {
            quizCreate()
        }
        else {
            questionsLayout.visibility = View.GONE
            reviewLayout.visibility = View.VISIBLE
            setUpRecyclerView()
        }
    }

    fun option2Click(view: View) {
        questionsList[c].selectedOption = option2.text.toString()
        c++
        if (c<10) {
            quizCreate()
        }
        else {
            questionsLayout.visibility = View.GONE
            reviewLayout.visibility = View.VISIBLE
            setUpRecyclerView()
        }
    }

    fun option3Click(view: View) {
        questionsList[c].selectedOption = option3.text.toString()
        c++
        if (c<10) {
            quizCreate()
        }
        else {
            questionsLayout.visibility = View.GONE
            reviewLayout.visibility = View.VISIBLE
            setUpRecyclerView()
        }
    }

    fun option4Click(view: View) {
        questionsList[c].selectedOption = option4.text.toString()
        c++
        if (c<10) {
            quizCreate()
        }
        else {
            questionsLayout.visibility = View.GONE
            reviewLayout.visibility = View.VISIBLE
            setUpRecyclerView()
        }
    }

    fun setUpRecyclerView(){
        myRecyclerView = findViewById(R.id.yourAnswersList) as RecyclerView
        myRecyclerView.setHasFixedSize(true)
        myRecyclerView.layoutManager = LinearLayoutManager(this)

        mAdapter.quizAnswersAdapter(questionsList, this)

        myRecyclerView.adapter = mAdapter
    }
}