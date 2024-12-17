package com.example.masterfootball

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.masterfootball.adapters.videosAdapter
import com.example.masterfootball.classes.Videos
import com.example.masterfootball.classes.Video
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class Videos : AppCompatActivity() {
    lateinit var myRecyclerView : RecyclerView
    val mAdapter : videosAdapter = videosAdapter({ video: Video -> reproducirVideo(video) })
    var videosList : MutableList<Video> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_videos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        loadSimple()
        setUpRecyclerView()
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
    }

    fun reproducirVideo(video: Video){
        if (video.unlocked) {
            val i = Intent(this, videoReproduction::class.java)

            i.putExtra("url", video.url)

            startActivity(i)
        }
        else {
            Snackbar.make(findViewById<View>(android.R.id.content),"Video bloqueado", Snackbar.LENGTH_LONG)
                .show()
        }
    }
}