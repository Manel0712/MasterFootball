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
import com.example.masterfootball.classes.bdConnection
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.DriverManager
import java.time.LocalDate
import java.time.LocalTime
import java.time.LocalDateTime
import java.sql.Date
import java.sql.Time
import java.time.ZoneOffset

class quiz1 : AppCompatActivity() {
    lateinit var myRecyclerView : RecyclerView
    var questionsList : MutableList<QuestionQuiz> = ArrayList()
    var c = 0
    var number = 0
    var id: Int = 0
    lateinit var question: TextView
    lateinit var option1: TextView
    lateinit var option2: TextView
    lateinit var option3: TextView
    lateinit var option4: TextView
    lateinit var notaText: TextView
    lateinit var questionsLayout: ConstraintLayout
    lateinit var reviewLayout: ConstraintLayout
    lateinit var noteLayout: ConstraintLayout
    var nota: Int = 0
    lateinit var data: Date
    lateinit var hora: Time
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
        id = intent.extras!!.getInt("idUser")
        question = findViewById(R.id.question)
        option1 = findViewById(R.id.opcio1)
        option2 = findViewById(R.id.opcio2)
        option3 = findViewById(R.id.opcio3)
        option4 = findViewById(R.id.opcio4)
        questionsLayout = findViewById(R.id.questionsLayaout)
        reviewLayout = findViewById(R.id.reviewLayaout)
        noteLayout = findViewById(R.id.finishLayaout)
        notaText = findViewById(R.id.cualificacion)
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

    fun acabarQuiz(view: View) {
        reviewLayout.visibility = View.GONE
        for (question in questionsList) {
            if (question.selectedOption.equals(question.correctOption)) {
                nota++
            }
        }
        guardaIntent(number)
        noteLayout.visibility = View.VISIBLE
        notaText.setText(notaText.text.toString() + " " + nota + "/10")
        if (nota >= 5) {
            abrirVideo(number)
            Snackbar.make(findViewById<View>(android.R.id.content),"Video " + number+1 + " desbloqueado", Snackbar.LENGTH_LONG)
                .show()
        }
    }

    fun guardaIntent(number: Int) {
        val fechaHoraActual: LocalDateTime = LocalDateTime.now()
        val fecha: LocalDate = fechaHoraActual.toLocalDate()
        data = Date(fecha.year - 1900, fecha.monthValue - 1, fecha.dayOfMonth)
        val horaSQL: LocalTime = fechaHoraActual.toLocalTime()
        hora = Time((horaSQL.hour * 3600 + horaSQL.minute * 60 + horaSQL.second) * 1000L)
        CoroutineScope(Dispatchers.IO).launch {
            val isQuizOpenSuccessful = guardaIntentConnection(number)
            runOnUiThread {
                if (isQuizOpenSuccessful != null) {
                    Snackbar.make(findViewById<View>(android.R.id.content),"respuesta registrada correctamente", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
    private fun guardaIntentConnection(numero: Int): Boolean {
        var dbconfiguration: bdConnection = bdConnection()
        var connection: Connection? = null
        return try {
            connection = DriverManager.getConnection(dbconfiguration.dbUrl, dbconfiguration.dbUser, dbconfiguration.dbPassword)

            val query = "INSERT INTO quizintents VALUES (?, ?, ?, ?, ?, ?)"
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setInt(1, 0)
            preparedStatement.setInt(2, numero)
            preparedStatement.setDate(3, data)
            preparedStatement.setTime(4, hora)
            preparedStatement.setInt(5, id)
            preparedStatement.setInt(6, nota)

            val resultSet = preparedStatement.executeUpdate()
            resultSet > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            connection?.close()
        }
    }

    fun abrirVideo(number: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val isQuizOpenSuccessful = openVideoConnection(number+1)
            runOnUiThread {
                if (isQuizOpenSuccessful != null) {
                }
            }
        }
    }

    private fun openVideoConnection(numero: Int): Boolean {
        var dbconfiguration: bdConnection = bdConnection()
        var connection: Connection? = null
        return try {
            connection = DriverManager.getConnection(dbconfiguration.dbUrl, dbconfiguration.dbUser, dbconfiguration.dbPassword)

            val query = "INSERT INTO videoopen VALUES (?, ?, ?, ?)"
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setInt(1, 0)
            preparedStatement.setString(2, "Video "+numero)
            preparedStatement.setInt(3, id)
            preparedStatement.setString(4, "true")

            val resultSet = preparedStatement.executeUpdate()
            resultSet > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            connection?.close()
        }
    }
}