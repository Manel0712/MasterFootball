package com.example.masterfootball

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.masterfootball.classes.email
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlin.random.Random

class MainActivity2 : AppCompatActivity() {
    lateinit var correo: String
    lateinit var verificationCode: TextInputEditText
    var random: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        correo = intent.extras!!.getString("email").toString()
        enviarEmail()
    }

    fun enviarEmail() {
        val email = email(correo, "dumg azqj necj nxtq")
        val destinatario = "manel.delahoz@mataro.epiaedu.cat"
        val asunto = "Verificacion de inicio de session"
        random = Random.nextInt(100000, 1000000)
        val mensaje = "Introduce el siguiente codigo para verificar el inicio de session\n${random}"
        Thread {
            email.sendEmail(destinatario, asunto, mensaje)
        }.start()
    }

    fun verificarLogin(view: View) {
        verificationCode = findViewById<TextInputEditText?>(R.id.textInputEditText3)
        if (verificationCode.text.toString().toInt()==random) {
            Snackbar.make(findViewById<View>(android.R.id.content), "Inicio de session verificado",
                Snackbar.LENGTH_LONG)
                .show()
        }
        else {
            Snackbar.make(findViewById<View>(android.R.id.content),"Codigo incorrecto",Snackbar.LENGTH_LONG)
                .show()
        }
    }
}