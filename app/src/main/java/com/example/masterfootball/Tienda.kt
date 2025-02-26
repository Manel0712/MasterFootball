package com.example.masterfootball

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.example.masterfootball.classes.Users
import com.example.masterfootball.classes.pointsMoneysANDGemsConfiguration
import com.example.masterfootball.classes.profileImageConfigure
import kotlinx.coroutines.launch

class Tienda : AppCompatActivity(){
    var id: Int = 0
    lateinit var user: Users
    lateinit var moneys: TextView
    lateinit var gems: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.botiga)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = getColor(R.color.black)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        supportActionBar?.setDisplayShowTitleEnabled(false)
        id = intent.extras!!.getInt("userId")
        moneys = findViewById(R.id.inputMonedas)
        gems = findViewById(R.id.inputGemas)
        lifecycleScope.launch {
            var points = pointsMoneysANDGemsConfiguration()
            user = points.profileConfigure(id)
            moneys.text = user.moneys.toString()
            gems.text = user.gems.toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        lifecycleScope.launch {
            var update = profileImageConfigure()
            update.profileConfigure(id)
            val imageUri: Uri = update.profileConfigure(id).toUri()
            if (imageUri.toString()!="null") {
                contentResolver.takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
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

            R.id.juegosBtn -> {
                val i = Intent(this, Juegos::class.java)
                i.putExtra("userId",id)
                startActivity(i)
                true
            }

            R.id.videosBtn -> {
                val i = Intent(this, Videos::class.java)
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

            R.id.quizBtn -> {
                val i = Intent(this, Quiz::class.java)
                i.putExtra("userId",id)
                startActivity(i)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}