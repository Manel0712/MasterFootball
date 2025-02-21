package com.example.masterfootball

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masterfootball.adapters.videosAdapter
import com.example.masterfootball.classes.Videos
import com.example.masterfootball.classes.Video
import com.example.masterfootball.classes.bdConnection
import com.example.masterfootball.classes.openQuiz
import com.example.masterfootball.classes.openVideo
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.DriverManager

class Videos : AppCompatActivity() {
    lateinit var myRecyclerView : RecyclerView
    val mAdapter : videosAdapter = videosAdapter({ video: Video -> reproducirVideo(video) })
    var videosList : MutableList<Video> = ArrayList()
    var videosOpen : MutableList<openVideo> = ArrayList()
    var id: Int = 0
    var i = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_videos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setDisplayShowTitleEnabled(false)

        id = intent.extras!!.getInt("userId")

        loadSimple()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = getColor(R.color.black)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setUpRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuBtn -> {
                finish()
                true
            }

            R.id.juegosBtn -> {
                val i = Intent(this, Juegos::class.java)
                i.putExtra("userId",id)
                startActivity(i)
                true
            }

            R.id.quizBtn -> {
                val i = Intent(this, Quiz::class.java)
                i.putExtra("userId",id)
                startActivity(i)
                true
            }

            R.id.perfilBtn -> {
                val i = Intent(this, perfil::class.java)
                i.putExtra("userId",id)
                startActivity(i)
                true
            }

            R.id.tiendaBtn -> {
                val i = Intent(this, Tienda::class.java)
                i.putExtra("userId",id)
                startActivity(i)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun setUpRecyclerView(){
        myRecyclerView = findViewById(R.id.videosList) as RecyclerView
        myRecyclerView.setHasFixedSize(true)
        myRecyclerView.layoutManager = LinearLayoutManager(this)

        mAdapter.videosAdapter(videosList, this)

        myRecyclerView.adapter = mAdapter
    }

    private fun loadSimple() {
        var json: String = this.assets.open("videos.json").bufferedReader().use { it.readText() }
        var gson: Gson = Gson()

        var videos: Videos = gson.fromJson(json, Videos::class.java)

        videos.videos.forEach{
            videosList.add(Video(it.name, it.url, it.unlocked))
        }

        open()
    }

    fun open() {
        CoroutineScope(Dispatchers.IO).launch {
            val isOpenSuccessful = openConnection()
            runOnUiThread {
                if (isOpenSuccessful != null) {
                    for (video in videosOpen) {
                        for (videoOpen in videosList) {
                            if (videoOpen.name.equals(video.videoName)) {
                                videosList[i].unlocked = true
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

    private fun openConnection(): MutableList<openVideo> {
        var dbconfiguration: bdConnection = bdConnection()
        var connection: Connection? = null
        return try {
            connection = DriverManager.getConnection(dbconfiguration.dbUrl, dbconfiguration.dbUser, dbconfiguration.dbPassword)

            val query = "SELECT * FROM videoopen WHERE usuari = ?"
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setInt(1, id)

            val resultSet = preparedStatement.executeQuery()
            while (resultSet.next()) {
                val id = resultSet.getInt("idvideoOpen")
                val quiz = resultSet.getString("videoName")
                val user = resultSet.getInt("usuari")
                val openStatus = resultSet.getString("openStatus")

                videosOpen.add(openVideo(id, quiz, user, openStatus))
            }
            videosOpen
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()
        } finally {
            connection?.close()
        }
    }

    fun reproducirVideo(video: Video){
        if (video.unlocked) {
            val i = Intent(this, videoReproduction::class.java)

            i.putExtra("url", video.url)
            i.putExtra("name", video.name)
            i.putExtra("userId",id)

            startActivity(i)
        }
        else {
            Snackbar.make(findViewById<View>(android.R.id.content),"Video bloqueado", Snackbar.LENGTH_LONG)
                .show()
        }
    }
}