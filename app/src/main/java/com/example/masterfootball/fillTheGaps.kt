package com.example.masterfootball

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class fillTheGaps : AppCompatActivity() {
    lateinit var frase: TextView
    lateinit var opcio1: TextView
    lateinit var opcio2: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fill_the_gaps)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        frase = findViewById(R.id.frase)
        opcio1 = findViewById(R.id.textView44)
        opcio2 = findViewById(R.id.textView43)
    }

    fun configurarFillTheGaps() {
        frase =
    }
}