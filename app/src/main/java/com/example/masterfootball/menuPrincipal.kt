package com.example.masterfootball

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlin.random.Random
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import java.security.MessageDigest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import com.example.masterfootball.classes.Users
import com.google.android.material.snackbar.Snackbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.example.masterfootball.classes.profileImageConfigure
import com.example.masterfootball.classes.updatePointsANDMoneys

class menuPrincipal: AppCompatActivity() {
    var id: Int = 0
    lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        supportActionBar?.hide()
        setContentView(R.layout.main_menu)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        id = intent.extras!!.getInt("userId")
        imageView = findViewById(R.id.imageView9)
        lifecycleScope.launch {
            var update = profileImageConfigure()
            update.profileConfigure(id)
            val imageUri: Uri = update.profileConfigure(id).toUri()
            val inputStream = contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            imageView.setImageBitmap(bitmap)
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            inputStream?.close()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    fun videosClick(view: View) {
        val i = Intent(this, Videos::class.java)
        i.putExtra("userId",id)
        startActivity(i)
    }

    fun juegosClick(view: View) {
        val i = Intent(this, Juegos::class.java)
        i.putExtra("userId",id)
        startActivity(i)
    }

    fun quizClick(view: View) {
        val i = Intent(this, Quiz::class.java)
        i.putExtra("userId",id)
        startActivity(i)
    }

    fun tiendaClick(view: View) {
        val i = Intent(this, Tienda::class.java)
        i.putExtra("userId",id)
        startActivity(i)
    }

    fun perfilOpen(view: View) {
        val i = Intent(this, perfil::class.java)
        i.putExtra("userId",id)
        startActivity(i)
    }
}