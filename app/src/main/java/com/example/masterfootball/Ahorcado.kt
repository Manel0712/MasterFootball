package com.example.masterfootball

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.masterfootball.classes.APIRequests
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import com.example.masterfootball.classes.Result
import com.example.masterfootball.classes.updatePointsANDMoneys
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlin.random.Random

class Ahorcado : AppCompatActivity() {
    var worldList : MutableList<Result>? = ArrayList()
    lateinit var correctWorld: String
    lateinit var newWorld: CharArray
    lateinit var world: TextView
    lateinit var letter: TextInputEditText
    val aVariants = listOf("a", "A", "á", "Á", "à", "À", "â", "Â", "ä", "Ä", "ȩ", "Ȩ")
    val eVariants = listOf("e", "E", "é", "É", "è", "È", "ê", "Ê", "ë", "Ë", "ę", "Ȩ")
    val iVariants = listOf("i", "I", "í", "Í", "ì", "Ì", "î", "Î", "ï", "Ï", "ĩ", "Ĩ", "į", "Į", "ı")
    val oVariants = listOf("o", "O", "ó", "Ó", "ò", "Ò", "ô", "Ô", "ö", "Ö", "õ", "Õ", "ø", "Ø", "œ", "Œ")
    val uVariants = listOf("u", "U", "ú", "Ú", "ù", "Ù", "û", "Û", "ü", "Ü", "ũ", "Ũ", "ų", "Ų", "ŭ", "Ŭ")
    var letterContentWorld: Boolean = false
    var letterAdd: Boolean = true
    var letterList: MutableList<String> = ArrayList()
    var id: Int = 0
    lateinit var textViewChange: TextView
    var c = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_horcado)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        id = intent.extras!!.getInt("userId")

        world = findViewById(R.id.textView26)

        lifecycleScope.launch {
            var request = APIRequests()
            var teamData = request.getBarcelonaPlayers()
            worldList = teamData
            selectCorrectWorld()
        }
    }

    fun selectCorrectWorld() {
        var random = Random.nextInt(worldList!![0].players.size)
        correctWorld = (worldList as MutableList)[0].players[random].playerName
        for (size in 0..correctWorld.length-1) {
            if (correctWorld[size].toString() != " ") {
                world.setText(world.text.toString() + "-")
            }
            else {
                world.setText(world.text.toString() + " ")
            }
        }
    }

    fun comprovedLetter(view: View) {
        letter = findViewById(R.id.editText25)
        if (letterList.size==0) {
            letterList.add(letter.text.toString())
        }
        else {
            for (a in 0..letterList.size-1) {
                if (letterList[a].equals(letter.text.toString())) {
                    letterAdd = false
                }
            }
        }
        if (letterAdd) {
            letterList.add(letter.text.toString())
            newWorld = world.text.toString().toCharArray()
            for (e in 0..correctWorld.length-1) {
                if (letter.text.toString()=="a") {
                    if (correctWorld[e].toString().equals(letter.text.toString())||correctWorld[e].toString().equals(letter.text.toString().uppercase())||correctWorld[e].toString().equals(letter.text.toString().lowercase())||correctWorld[e].toString().equals(aVariants[0])||correctWorld[e].toString().equals(aVariants[1])||correctWorld[e].toString().equals(aVariants[2])||correctWorld[e].toString().equals(aVariants[3])||correctWorld[e].toString().equals(aVariants[4])||correctWorld[e].toString().equals(aVariants[5])||correctWorld[e].toString().equals(aVariants[6])||correctWorld[e].toString().equals(aVariants[7])||correctWorld[e].toString().equals(aVariants[8])||correctWorld[e].toString().equals(aVariants[9])||correctWorld[e].toString().equals(aVariants[10])||correctWorld[e].toString().equals(aVariants[11])) {
                        newWorld[e] = correctWorld[e]
                        letterContentWorld = true
                    }
                }
                else if (letter.text.toString()=="e") {
                    if (correctWorld[e].toString().equals(letter.text.toString())||correctWorld[e].toString().equals(letter.text.toString().uppercase())||correctWorld[e].toString().equals(letter.text.toString().lowercase())||correctWorld[e].toString().equals(eVariants[0])||correctWorld[e].toString().equals(eVariants[1])||correctWorld[e].toString().equals(eVariants[2])||correctWorld[e].toString().equals(eVariants[3])||correctWorld[e].toString().equals(eVariants[4])||correctWorld[e].toString().equals(eVariants[5])||correctWorld[e].toString().equals(eVariants[6])||correctWorld[e].toString().equals(eVariants[7])||correctWorld[e].toString().equals(eVariants[8])||correctWorld[e].toString().equals(eVariants[9])||correctWorld[e].toString().equals(eVariants[10])||correctWorld[e].toString().equals(eVariants[11])) {
                        newWorld[e] = correctWorld[e]
                        letterContentWorld = true
                    }
                }
                else if (letter.text.toString()=="i") {
                    if (correctWorld[e].toString().equals(letter.text.toString())||correctWorld[e].toString().equals(letter.text.toString().uppercase())||correctWorld[e].toString().equals(letter.text.toString().lowercase())||correctWorld[e].toString().equals(iVariants[0])||correctWorld[e].toString().equals(iVariants[1])||correctWorld[e].toString().equals(iVariants[2])||correctWorld[e].toString().equals(iVariants[3])||correctWorld[e].toString().equals(iVariants[4])||correctWorld[e].toString().equals(iVariants[5])||correctWorld[e].toString().equals(iVariants[6])||correctWorld[e].toString().equals(iVariants[7])||correctWorld[e].toString().equals(iVariants[8])||correctWorld[e].toString().equals(iVariants[9])||correctWorld[e].toString().equals(iVariants[10])||correctWorld[e].toString().equals(iVariants[11])||correctWorld[e].toString().equals(iVariants[12])||correctWorld[e].toString().equals(iVariants[13])||correctWorld[e].toString().equals(iVariants[14])) {
                        newWorld[e] = correctWorld[e]
                        letterContentWorld = true
                    }
                }
                else if (letter.text.toString()=="o") {
                    if (correctWorld[e].toString().equals(letter.text.toString())||correctWorld[e].toString().equals(letter.text.toString().uppercase())||correctWorld[e].toString().equals(letter.text.toString().lowercase())||correctWorld[e].toString().equals(oVariants[0])||correctWorld[e].toString().equals(oVariants[1])||correctWorld[e].toString().equals(oVariants[2])||correctWorld[e].toString().equals(oVariants[3])||correctWorld[e].toString().equals(oVariants[4])||correctWorld[e].toString().equals(oVariants[5])||correctWorld[e].toString().equals(oVariants[6])||correctWorld[e].toString().equals(oVariants[7])||correctWorld[e].toString().equals(oVariants[8])||correctWorld[e].toString().equals(oVariants[9])||correctWorld[e].toString().equals(oVariants[10])||correctWorld[e].toString().equals(oVariants[11])||correctWorld[e].toString().equals(oVariants[12])||correctWorld[e].toString().equals(oVariants[13])||correctWorld[e].toString().equals(oVariants[14])||correctWorld[e].toString().equals(oVariants[15])) {
                        newWorld[e] = correctWorld[e]
                        letterContentWorld = true
                    }
                }
                else if (letter.text.toString()=="u") {
                    if (correctWorld[e].toString().equals(letter.text.toString())||correctWorld[e].toString().equals(letter.text.toString().uppercase())||correctWorld[e].toString().equals(letter.text.toString().lowercase())||correctWorld[e].toString().equals(uVariants[0])||correctWorld[e].toString().equals(uVariants[1])||correctWorld[e].toString().equals(uVariants[2])||correctWorld[e].toString().equals(uVariants[3])||correctWorld[e].toString().equals(uVariants[4])||correctWorld[e].toString().equals(uVariants[5])||correctWorld[e].toString().equals(uVariants[6])||correctWorld[e].toString().equals(uVariants[7])||correctWorld[e].toString().equals(uVariants[8])||correctWorld[e].toString().equals(uVariants[9])||correctWorld[e].toString().equals(uVariants[10])||correctWorld[e].toString().equals(uVariants[11])||correctWorld[e].toString().equals(uVariants[12])||correctWorld[e].toString().equals(uVariants[13])||correctWorld[e].toString().equals(uVariants[14])||correctWorld[e].toString().equals(uVariants[15])) {
                        newWorld[e] = correctWorld[e]
                        letterContentWorld = true
                    }
                }
                else {
                    if (correctWorld[e].toString().equals(letter.text.toString())||correctWorld[e].toString().equals(letter.text.toString().uppercase())||correctWorld[e].toString().equals(letter.text.toString().lowercase())) {
                        newWorld[e] = correctWorld[e]
                        letterContentWorld = true
                    }
                }
            }
            if (letterContentWorld) {
                world.text = ""
                if (String(newWorld).equals(correctWorld)) {
                    world.setTextColor(ContextCompat.getColor(this, R.color.background))
                    world.setText(String(newWorld))
                    Snackbar.make(findViewById<View>(android.R.id.content),"Has encertat, enhorabona", Snackbar.LENGTH_LONG)
                        .show()
                    lifecycleScope.launch {
                        var update = updatePointsANDMoneys()
                        update.updatePointsANDMoneys(5, 5, 2, id)
                    }
                }
                else {
                    world.setText(String(newWorld))
                }
            }
            else {
                unaVidaMenys()
            }
        }
        else {
            unaVidaMenys()
        }
        letterAdd = true
        letterContentWorld = false
        letter.setText("")
    }

    fun responseDialogBasic(view: View) {
        var respuesta: EditText = EditText(this)
        MaterialAlertDialogBuilder(this)
            .setTitle("Resolver")
            .setCancelable(false)
            .setView(respuesta)
            .setMessage("Tu respuesta")
            .setNeutralButton("Cancelar",null)
            .setPositiveButton("Comprovar") { dialog, which ->
                if (respuesta.text.toString().equals(correctWorld)) {
                    world.setTextColor(ContextCompat.getColor(this, R.color.background))
                    world.setText(respuesta.text.toString())
                    Snackbar.make(findViewById<View>(android.R.id.content),"Has encertat, enhorabona", Snackbar.LENGTH_LONG)
                        .show()
                    lifecycleScope.launch {
                        var update = updatePointsANDMoneys()
                        update.updatePointsANDMoneys(5, 5, 2, id)
                    }
                }
                else {
                    Snackbar.make(findViewById<View>(android.R.id.content),"Resposta incorrecta", Snackbar.LENGTH_LONG)
                        .show()
                    unaVidaMenys()
                }
            }
            .show()

    }

    fun unaVidaMenys() {
        if (c==0) {
            textViewChange = findViewById(R.id.textView27)
            textViewChange.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
        else if (c==1) {
            textViewChange = findViewById(R.id.textView28)
            textViewChange.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
        else if (c==2) {
            textViewChange = findViewById(R.id.textView29)
            textViewChange.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
        else if (c==3) {
            textViewChange = findViewById(R.id.textView30)
            textViewChange.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
        else if (c==4) {
            textViewChange = findViewById(R.id.textView31)
            textViewChange.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
        else if (c==5) {
            textViewChange = findViewById(R.id.textView32)
            textViewChange.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
        else if (c==6) {
            textViewChange = findViewById(R.id.textView33)
            textViewChange.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
        else if (c==7) {
            textViewChange = findViewById(R.id.textView34)
            textViewChange.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
        else if (c==8) {
            textViewChange = findViewById(R.id.textView35)
            textViewChange.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
        else if (c==9) {
            textViewChange = findViewById(R.id.textView36)
            textViewChange.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
        else if (c==10) {
            world.setTextColor(ContextCompat.getColor(this, R.color.red))
            world.setText(correctWorld)
            Snackbar.make(findViewById<View>(android.R.id.content),"Mes sort la propera vegada", Snackbar.LENGTH_LONG)
                .show()
        }
        c++
    }
}