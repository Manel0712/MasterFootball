package com.example.masterfootball

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.masterfootball.classes.Quiz
import com.example.masterfootball.classes.Video
import com.example.masterfootball.classes.Videos
import com.example.masterfootball.classes.frase
import com.example.masterfootball.classes.Frases
import android.content.ClipData
import android.view.DragEvent
import android.view.Menu
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlin.random.Random
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import com.example.masterfootball.classes.updatePointsANDMoneys
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class fillTheGaps : AppCompatActivity() {
    var id: Int = 0
    lateinit var frase: TextView
    lateinit var fraseOriginal: String
    lateinit var opcio1: TextView
    lateinit var opcio2: TextView
    lateinit var correctOption: String
    lateinit var option: String
    var frasesList : MutableList<frase> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fill_the_gaps)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayShowTitleEnabled(false)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = getColor(R.color.black)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        frase = findViewById(R.id.frase)
        opcio1 = findViewById(R.id.textView44)
        opcio2 = findViewById(R.id.textView43)
        frase.setOnDragListener { view, event -> handleDrop(view, event) }
        opcio1.setOnLongClickListener { startDrag(it) }
        opcio2.setOnLongClickListener { startDrag(it) }
        fraseOriginal = frase.text.toString()
        configurarFillTheGaps()
        id = intent.extras!!.getInt("userId")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    private fun startDrag(view: View): Boolean {
        val clipData = ClipData.newPlainText("", (view as TextView).text)
        val shadowBuilder = View.DragShadowBuilder(view)
        view.startDragAndDrop(clipData, shadowBuilder, view, 0)
        return true
    }

    private fun handleDrop(view: View, event: DragEvent): Boolean {
        when (event.action) {
            DragEvent.ACTION_DROP -> {
                val droppedText = event.clipData.getItemAt(0).text.toString()
                frase.text = fraseOriginal + " " + droppedText
            }
        }
        return true
    }

    fun configurarFillTheGaps() {
        getFrases()
        var random = Random.nextInt(frasesList.size)
        frase.text = frasesList[random].frase
        opcio1.text = frasesList[random].opcion_1
        opcio2.text = frasesList[random].opcion_2
        fraseOriginal = frase.text.toString()
        correctOption = frase.text.toString() + " " + frasesList[random].respuesta_correcta
        option = frasesList[random].respuesta_correcta
    }

    fun getFrases() {
        var json: String = this.assets.open("frases.json").bufferedReader().use { it.readText() }
        var gson: Gson = Gson()

        var frases: Frases = gson.fromJson(json, Frases::class.java)

        frases.frases.forEach{
            frasesList.add(frase(it.frase, it.opcion_1, it.opcion_2, it.respuesta_correcta))
        }
    }

    fun sentencecheck(view: View) {
        if (frase.text.toString().equals(correctOption)) {
            frase.setBackgroundColor(ContextCompat.getColor(this, R.color.background))
            lifecycleScope.launch {
                var update = updatePointsANDMoneys()
                update.updatePointsANDMoneys(5, 5, 2, id)
            }
        }
        else {
            frase.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
            Snackbar.make(findViewById<View>(android.R.id.content),"La resposta correcta es " + option, Snackbar.LENGTH_LONG)
                .show()
        }
    }
}