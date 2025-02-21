package com.example.masterfootball

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.example.masterfootball.classes.Users
import com.example.masterfootball.classes.pointsMoneysANDGemsConfiguration
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