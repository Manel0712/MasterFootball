package com.example.masterfootball

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.view.View

class Juegos: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.jocs_extres)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    fun openHorcado(view: View) {
        val i = Intent(this, Horcado::class.java)
        startActivity(i)
    }

    fun openWoordle(view: View) {
        val i = Intent(this, Woordle::class.java)
        startActivity(i)
    }

    fun openTrivial(view: View) {
        val i = Intent(this, trivial::class.java)
        startActivity(i)
    }

    fun openFillTheGaps(view: View) {
        val i = Intent(this, fillTheGaps::class.java)
        startActivity(i)
    }
}