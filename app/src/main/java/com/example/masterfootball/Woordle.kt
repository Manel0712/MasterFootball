package com.example.masterfootball

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.transition.Visibility
import com.google.android.material.textfield.TextInputEditText
import androidx.activity.enableEdgeToEdge

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
        lletra1 = findViewById(R.id.editText10)
        lletra2 = findViewById(R.id.editText12)
        lletra3 = findViewById(R.id.editText13)
        lletra4 = findViewById(R.id.editText14)
        lletra5 = findViewById(R.id.editText11)
        lletra6 = findViewById(R.id.editText4)
        lletra7 = findViewById(R.id.editText)
        lletra8 = findViewById(R.id.editText2)
        lletra9 = findViewById(R.id.editText3)
        lletra10 = findViewById(R.id.textInputEditText)
        lletra11 = findViewById(R.id.editText5)
        lletra12 = findViewById(R.id.editText7)
        lletra13 = findViewById(R.id.editText8)
        lletra14 = findViewById(R.id.editText9)
        lletra15 = findViewById(R.id.editText6)
        lletra16 = findViewById(R.id.editText15)
        lletra17 = findViewById(R.id.editText17)
        lletra18 = findViewById(R.id.editText18)
        lletra19 = findViewById(R.id.editText19)
        lletra20 = findViewById(R.id.editText16)
        lletra21 = findViewById(R.id.editText20)
        lletra22 = findViewById(R.id.editText22)
        lletra23 = findViewById(R.id.editText23)
        lletra24 = findViewById(R.id.editText24)
        lletra25 = findViewById(R.id.editText21)
        resulText = findViewById(R.id.textView37)
        comprovatedButton = findViewById(R.id.button9)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = getColor(R.color.black)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        supportActionBar?.setDisplayShowTitleEnabled(false)
        woordleConfigure()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    fun woordleConfigure() {
        paraulaCorrecta = paraulesFutbol.random()
        lletresParaulaCorrecta[0] = paraulaCorrecta[0].toString()
        lletresParaulaCorrecta[1] = paraulaCorrecta[1].toString()
        lletresParaulaCorrecta[2] = paraulaCorrecta[2].toString()
        lletresParaulaCorrecta[3] = paraulaCorrecta[3].toString()
        lletresParaulaCorrecta[4] = paraulaCorrecta[4].toString()
    }

    fun verificarParaula(view: View) {
        if (intent==0) {
            verificarLletresParaula(lletra1, lletra2, lletra3, lletra4, lletra5)
            lletra1.isEnabled = false
            lletra2.isEnabled = false
            lletra3.isEnabled = false
            lletra4.isEnabled = false
            lletra5.isEnabled = false
            if (lletresCorrectes==5) {
                comprovatedButton.visibility = View.GONE
                resulText.visibility = View.VISIBLE
                resulText.text = "Has encertat la paraula del dia"
            }
            else {
                lletra6.isEnabled = true
                lletra7.isEnabled = true
                lletra8.isEnabled = true
                lletra9.isEnabled = true
                lletra10.isEnabled = true
                lletresCorrectes = 0
            }
            intent++
        }
        else if (intent==1) {
            verificarLletresParaula(lletra6, lletra7, lletra8, lletra9, lletra10)
            lletra6.isEnabled = false
            lletra7.isEnabled = false
            lletra8.isEnabled = false
            lletra9.isEnabled = false
            lletra10.isEnabled = false
            if (lletresCorrectes==5) {
                comprovatedButton.visibility = View.GONE
                resulText.visibility = View.VISIBLE
                resulText.text = "Has encertat la paraula del dia"
            }
            else {
                lletra11.isEnabled = true
                lletra12.isEnabled = true
                lletra13.isEnabled = true
                lletra14.isEnabled = true
                lletra15.isEnabled = true
                lletresCorrectes = 0
            }
            intent++
        }
        else if (intent==2) {
            verificarLletresParaula(lletra11, lletra12, lletra13, lletra14, lletra15)
            lletra11.isEnabled = false
            lletra12.isEnabled = false
            lletra13.isEnabled = false
            lletra14.isEnabled = false
            lletra15.isEnabled = false
            if (lletresCorrectes==5) {
                comprovatedButton.visibility = View.GONE
                resulText.visibility = View.VISIBLE
                resulText.text = "Has encertat la paraula del dia"
            }
            else {
                lletra16.isEnabled = true
                lletra17.isEnabled = true
                lletra18.isEnabled = true
                lletra19.isEnabled = true
                lletra20.isEnabled = true
                lletresCorrectes = 0
            }
            intent++
        }
        else if (intent==3) {
            verificarLletresParaula(lletra16, lletra17, lletra18, lletra19, lletra20)
            lletra16.isEnabled = false
            lletra17.isEnabled = false
            lletra18.isEnabled = false
            lletra19.isEnabled = false
            lletra20.isEnabled = false
            if (lletresCorrectes==5) {
                comprovatedButton.visibility = View.GONE
                resulText.visibility = View.VISIBLE
                resulText.text = "Has encertat la paraula del dia"
            }
            else {
                lletra21.isEnabled = true
                lletra22.isEnabled = true
                lletra23.isEnabled = true
                lletra24.isEnabled = true
                lletra25.isEnabled = true
                lletresCorrectes = 0
            }
            intent++
        }
        else if (intent==4) {
            verificarLletresParaula(lletra21, lletra22, lletra23, lletra24, lletra25)
            lletra21.isEnabled = false
            lletra22.isEnabled = false
            lletra23.isEnabled = false
            lletra24.isEnabled = false
            lletra25.isEnabled = false
            if (lletresCorrectes==5) {
                comprovatedButton.visibility = View.GONE
                resulText.visibility = View.VISIBLE
                resulText.text = "Has encertat la paraula del dia"
            }
            else {
                comprovatedButton.visibility = View.GONE
                resulText.visibility = View.VISIBLE
                resulText.text = "La paraula correcta es " + paraulaCorrecta
            }
        }
    }

    fun verificarLletresParaula(lletra1: TextInputEditText, lletra2: TextInputEditText, lletra3: TextInputEditText, lletra4: TextInputEditText, lletra5: TextInputEditText) {
        if (lletra1.text.toString().equals(lletresParaulaCorrecta[0])) {
            lletra1.setBackgroundColor(ContextCompat.getColor(this, R.color.background))
            lletresCorrectes++
        }
        else if (lletra1.text.toString().equals(lletresParaulaCorrecta[1])||lletra1.text.toString().equals(lletresParaulaCorrecta[2])||lletra1.text.toString().equals(lletresParaulaCorrecta[3])||lletra1.text.toString().equals(lletresParaulaCorrecta[4])) {
            lletra1.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
        }
        else {
            lletra1.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
        if (lletra2.text.toString().equals(lletresParaulaCorrecta[1])) {
            lletra2.setBackgroundColor(ContextCompat.getColor(this, R.color.background))
            lletresCorrectes++
        }
        else if (lletra2.text.toString().equals(lletresParaulaCorrecta[0])||lletra2.text.toString().equals(lletresParaulaCorrecta[2])||lletra2.text.toString().equals(lletresParaulaCorrecta[3])||lletra2.text.toString().equals(lletresParaulaCorrecta[4])) {
            lletra2.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
        }
        else {
            lletra2.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
        if (lletra3.text.toString().equals(lletresParaulaCorrecta[2])) {
            lletra3.setBackgroundColor(ContextCompat.getColor(this, R.color.background))
            lletresCorrectes++
        }
        else if (lletra3.text.toString().equals(lletresParaulaCorrecta[1])||lletra3.text.toString().equals(lletresParaulaCorrecta[0])||lletra3.text.toString().equals(lletresParaulaCorrecta[3])||lletra3.text.toString().equals(lletresParaulaCorrecta[4])) {
            lletra3.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
        }
        else {
            lletra3.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
        if (lletra4.text.toString().equals(lletresParaulaCorrecta[3])) {
            lletra4.setBackgroundColor(ContextCompat.getColor(this, R.color.background))
            lletresCorrectes++
        }
        else if (lletra4.text.toString().equals(lletresParaulaCorrecta[1])||lletra4.text.toString().equals(lletresParaulaCorrecta[2])||lletra4.text.toString().equals(lletresParaulaCorrecta[0])||lletra4.text.toString().equals(lletresParaulaCorrecta[4])) {
            lletra4.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
        }
        else {
            lletra4.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
        if (lletra5.text.toString().equals(lletresParaulaCorrecta[4])) {
            lletra5.setBackgroundColor(ContextCompat.getColor(this, R.color.background))
            lletresCorrectes++
        }
        else if (lletra5.text.toString().equals(lletresParaulaCorrecta[1])||lletra5.text.toString().equals(lletresParaulaCorrecta[2])||lletra5.text.toString().equals(lletresParaulaCorrecta[3])||lletra5.text.toString().equals(lletresParaulaCorrecta[0])) {
            lletra5.setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
        }
        else {
            lletra5.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
    }
}