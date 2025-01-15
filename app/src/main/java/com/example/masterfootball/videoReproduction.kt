package com.example.masterfootball

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import com.example.masterfootball.classes.QuestionQuiz
import com.example.masterfootball.classes.Quiz
import com.example.masterfootball.classes.Quizs
import com.example.masterfootball.classes.Video
import com.example.masterfootball.classes.Videos
import com.example.masterfootball.classes.bdConnection
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.file.Files
import java.nio.file.Paths
import java.sql.Connection
import java.sql.DriverManager
import kotlin.io.path.Path

class videoReproduction : AppCompatActivity() {
    lateinit var videoUrl: String
    lateinit var videoName: String
    var id: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_video_reproduction)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        id = intent.extras!!.getInt("userId")
        videoUrl = intent.extras!!.getString("url").toString()
        videoName = intent.extras!!.getString("name").toString()
        val youTubePlayerView = findViewById<YouTubePlayerView>(R.id.youtubePlayerView)
        lifecycle.addObserver(youTubePlayerView)
        val videoId = extractYouTubeVideoId(videoUrl)

        if (videoId != null) {
            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            })
        } else {
        }

        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }
    private fun extractYouTubeVideoId(url: String): String? {
        val regex = Regex("(?<=v=|youtu.be/)[^&#?]+")
        return regex.find(url)?.value
    }
    fun quiz(view: View) {
        val separedText = videoName.split(" ")
        val number = separedText[1]
        abrirQuiz(number.toInt())
    }

    fun abrirQuiz(number: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val isQuizOpenSuccessful = registreConnection(number)
            runOnUiThread {
                if (isQuizOpenSuccessful != null) {
                    startActivity(number)
                }
            }
        }
    }

    private fun registreConnection(numero: Int): Boolean {
        var dbconfiguration: bdConnection = bdConnection()
        var connection: Connection? = null
        return try {
            connection = DriverManager.getConnection(dbconfiguration.dbUrl, dbconfiguration.dbUser, dbconfiguration.dbPassword)

            val query = "INSERT INTO quizopen VALUES (?, ?, ?, ?)"
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setInt(1, 0)
            preparedStatement.setString(2, "Quiz "+numero)
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

    private fun startActivity(number: Int) {
        val i = Intent(this, quiz1::class.java)

        i.putExtra("number", number)
        i.putExtra("idUser", id)

        startActivity(i)
    }
}