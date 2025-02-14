package com.example.masterfootball

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

class Tienda : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.botiga)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = getColor(R.color.black)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}