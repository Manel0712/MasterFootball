package com.example.masterfootball

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.masterfootball.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlin.random.Random
import android.view.View

class MainActivity : AppCompatActivity() {

    lateinit var inputUser: TextInputEditText
    lateinit var inputPass: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inputUser = findViewById(R.id.textInputEditText)
        inputPass = findViewById(R.id.textInputEditText2)
    }

    fun login(view: View) {
        if (inputUser.text!!.toString().equals("mdelahoz") && inputPass.text!!.toString().equals("Delahoz0712@")) {
            val i = Intent(this, MainActivity2::class.java)

            i.putExtra("email","maneldelahozrodriguez@gmail.com")

            startActivity(i)
        }
    }
}