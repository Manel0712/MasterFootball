package com.example.masterfootball

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Guies : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_guies)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val videoView1: VideoView = findViewById(R.id.videoView1)
        val videoView2: VideoView = findViewById(R.id.videoView2)
        val mediaController1 = MediaController(this)
        val mediaController2 = MediaController(this)
        videoView1.setMediaController(mediaController1)
        videoView2.setMediaController(mediaController2)
        val videoUri1 = Uri.parse("android.resource://$packageName/raw/video1")
        val videoUri2 = Uri.parse("android.resource://$packageName/raw/video2")
        videoView1.setVideoURI(videoUri1)
        videoView2.setVideoURI(videoUri2)
        videoView1.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                openFullscreen(videoUri1, videoView1.currentPosition)
            }
            false
        }

        videoView2.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                openFullscreen(videoUri2, videoView2.currentPosition)
            }
            false
        }
    }
    private fun openFullscreen(videoUri: Uri, position: Int) {
        val intent = Intent(this, FullscreenVideoActivity::class.java)
        intent.putExtra("VIDEO_URI", videoUri.toString())
        intent.putExtra("VIDEO_POSITION", position)
        startActivity(intent)
    }
}