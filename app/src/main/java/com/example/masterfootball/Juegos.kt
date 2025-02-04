package com.example.masterfootball

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.view.View

class Juegos: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.jocs_extres)
    }

    fun openTrivial(view: View) {
        val i = Intent(this, trivial::class.java)
        startActivity(i)
    }
}