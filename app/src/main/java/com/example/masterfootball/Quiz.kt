package com.example.masterfootball

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masterfootball.adapters.quizAdapter
import com.example.masterfootball.classes.Quiz
import com.example.masterfootball.classes.Quizs
import com.example.masterfootball.classes.bdConnection
import com.example.masterfootball.classes.openQuiz
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.DriverManager

class Quiz : AppCompatActivity() {
    lateinit var myRecyclerView : RecyclerView
    val mAdapter : quizAdapter = quizAdapter({ quiz: Quiz -> jugarQuiz(quiz) })
    var quizsList : MutableList<Quiz> = ArrayList()
    var quizsOpen : MutableList<openQuiz> = ArrayList()
    var id: Int = 0
    var i = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.quiz)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        id = intent.extras!!.getInt("userId")
        supportActionBar?.setDisplayShowTitleEnabled(false)
        loadSimple()
        setUpRecyclerView()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    fun setUpRecyclerView(){
        myRecyclerView = findViewById(R.id.quizList) as RecyclerView
        myRecyclerView.setHasFixedSize(true)
        myRecyclerView.layoutManager = LinearLayoutManager(this)

        mAdapter.quizAdapter(quizsList, this)

        myRecyclerView.adapter = mAdapter
    }

    private fun loadSimple() {
        var json: String = this.assets.open("quizs.json").bufferedReader().use { it.readText() }
        var gson: Gson = Gson()

        var quizs: Quizs = gson.fromJson(json, Quizs::class.java)

        quizs.quizs.forEach{
            quizsList.add(Quiz(it.name, it.unlocked))
        }

        open()
    }

    fun open() {
        CoroutineScope(Dispatchers.IO).launch {
            val isOpenSuccessful = openConnection()
            runOnUiThread {
                if (isOpenSuccessful != null) {
                    for (quiz in quizsOpen) {
                        for (quiz1 in quizsList) {
                            if (quiz1.name.equals(quiz.quizName)) {
                                quizsList[i].unlocked = true
                                mAdapter.notifyDataSetChanged()
                            }
                            i++
                        }
                        i = 0
                    }
                } else {
                    Snackbar.make(findViewById<View>(android.R.id.content),"Usuario o contraseña incorrectos.",
                        Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun openConnection(): MutableList<openQuiz> {
        var dbconfiguration: bdConnection = bdConnection()
        var connection: Connection? = null
        return try {
            connection = DriverManager.getConnection(dbconfiguration.dbUrl, dbconfiguration.dbUser, dbconfiguration.dbPassword)

            val query = "SELECT * FROM quizopen WHERE idquizOpen = ?"
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setInt(1, id)

            val resultSet = preparedStatement.executeQuery()
            while (resultSet.next()) {
                val id = resultSet.getInt("idquizOpen")
                val quiz = resultSet.getString("quizName")
                val user = resultSet.getInt("usuari")
                val openStatus = resultSet.getString("openStatus")

                quizsOpen.add(openQuiz(id, quiz, user, openStatus))
            }
            quizsOpen
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()
        } finally {
            connection?.close()
        }
    }

    fun jugarQuiz(quiz: Quiz){
        if (quiz.unlocked) {
            val i = Intent(this, videoReproduction::class.java)

            startActivity(i)
        }
        else {
            Snackbar.make(findViewById<View>(android.R.id.content),"Quiz bloqueado", Snackbar.LENGTH_LONG)
                .show()
        }
    }
}