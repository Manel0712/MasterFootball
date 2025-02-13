package com.example.masterfootball

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.transition.Visibility

class Woordle : AppCompatActivity() {
    lateinit var lletra1: TextInputEditText
    lateinit var lletra2: TextInputEditText
    lateinit var lletra3: TextInputEditText
    lateinit var lletra4: TextInputEditText
    lateinit var lletra5: TextInputEditText
    lateinit var lletra6: TextInputEditText
    lateinit var lletra7: TextInputEditText
    lateinit var lletra8: TextInputEditText
    lateinit var lletra9: TextInputEditText
    lateinit var lletra10: TextInputEditText
    lateinit var lletra12: TextInputEditText
    lateinit var lletra13: TextInputEditText
    lateinit var lletra14: TextInputEditText
    lateinit var lletra15: TextInputEditText
    lateinit var lletra16: TextInputEditText
    lateinit var lletra17: TextInputEditText
    lateinit var lletra18: TextInputEditText
    lateinit var lletra19: TextInputEditText
    lateinit var lletra20: TextInputEditText
    lateinit var lletra21: TextInputEditText
    lateinit var lletra22: TextInputEditText
    lateinit var lletra23: TextInputEditText
    lateinit var lletra24: TextInputEditText
    lateinit var lletra25: TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.wordle)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}