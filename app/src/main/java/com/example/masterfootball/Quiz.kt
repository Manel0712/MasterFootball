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
import com.example.masterfootball.classes.Video
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class Quiz : AppCompatActivity() {
    lateinit var myRecyclerView : RecyclerView
    val mAdapter : quizAdapter = quizAdapter({ quiz: Quiz -> jugarQuiz(quiz) })
    var quizsList : MutableList<Quiz> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.quiz)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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