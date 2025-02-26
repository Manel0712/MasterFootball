package com.example.masterfootball

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.example.masterfootball.classes.Users
import com.example.masterfootball.classes.pointsMoneysANDGemsConfiguration
import com.example.masterfootball.classes.profileImageConfigure
import kotlinx.coroutines.launch

class Juegos: AppCompatActivity() {
    var id: Int = 0
    lateinit var user: Users
    lateinit var pointsText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.jocs_extres)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = getColor(R.color.black)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        supportActionBar?.setDisplayShowTitleEnabled(false)
        id = intent.extras!!.getInt("userId")
        pointsText = findViewById(R.id.textView16)
        lifecycleScope.launch {
            var points = pointsMoneysANDGemsConfiguration()
            user = points.profileConfigure(id)
            pointsText.text = user.points.toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        lifecycleScope.launch {
            var update = profileImageConfigure()
            update.profileConfigure(id)
            val imageUri: Uri = update.profileConfigure(id).toUri()
            if (imageUri.toString()!="null") {
                val inputStream = contentResolver.openInputStream(imageUri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val drawable = BitmapDrawable(resources, bitmap)
                val item = menu?.findItem(R.id.perfilBtn)
                item?.setIcon(drawable)
                inputStream?.close()
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuBtn -> {
                finish()
                true
            }

            R.id.videosBtn -> {
                val i = Intent(this, Videos::class.java)
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

    fun openHorcado(view: View) {
        val i = Intent(this, Ahorcado::class.java)
        i.putExtra("userId",id)
        startActivity(i)
    }

    fun openWoordle(view: View) {
        val i = Intent(this, Woordle::class.java)
        i.putExtra("userId",id)
        startActivity(i)
    }

    fun openTrivial(view: View) {
        val i = Intent(this, trivial::class.java)
        i.putExtra("userId",id)
        startActivity(i)
    }

    fun openFillTheGaps(view: View) {
        val i = Intent(this, fillTheGaps::class.java)
        i.putExtra("userId",id)
        startActivity(i)
    }

    fun open4Fotos1Palabra(view: View) {
        val i = Intent(this, UnaPalabra::class.java)
        i.putExtra("userId",id)
        startActivity(i)
    }
}