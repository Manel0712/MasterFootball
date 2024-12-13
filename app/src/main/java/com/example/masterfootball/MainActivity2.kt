package com.example.masterfootball

import android.content.Intent
import android.os.Bundle
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
        setContentView(R.layout.codi_verificacio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        correo = intent.extras!!.getString("email").toString()
        enviarEmail()
    }

    fun enviarEmail() {
        val email = email("maneldelahozrodriguez@gmail.com", "dumg azqj necj nxtq")
        val destinatario = correo
        val asunto = "Verificacion de inicio de session"
        random = Random.nextInt(100000, 1000000)
        val mensaje = "Introduce el siguiente codigo para verificar el inicio de session\n${random}"
        Thread {
            email.sendEmail(destinatario, asunto, mensaje)
        }.start()
    }

    fun verificarLogin(view: View) {
        verificationCode = findViewById<TextInputEditText?>(R.id.textInputEditText3)
        var code = verificationCode.text.toString().toInt()
        if (code==random) {
            val i = Intent(this, menuPrincipal::class.java)
            startActivity(i)
        }
        else {
            Snackbar.make(findViewById<View>(android.R.id.content),"Codigo incorrecto",Snackbar.LENGTH_LONG)
                .show()
        }
    }
}