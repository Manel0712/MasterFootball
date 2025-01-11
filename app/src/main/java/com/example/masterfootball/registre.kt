package com.example.masterfootball

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import android.view.View
import java.security.MessageDigest
import com.google.android.material.snackbar.Snackbar

class registre: AppCompatActivity() {
    lateinit var name: TextInputEditText
    lateinit var surname1: TextInputEditText
    lateinit var surname2: TextInputEditText
    lateinit var username: TextInputEditText
    lateinit var email: TextInputEditText
    lateinit var pass: TextInputEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registre)
    }
    fun registrarUsuari(view: View) {
        name = findViewById(R.id.inputNom)
        surname1 = findViewById(R.id.inputCognom1)
        surname2 = findViewById(R.id.inputCognom2)
        username = findViewById(R.id.inputUsuari)
        email = findViewById(R.id.inputEmail)
        pass = findViewById(R.id.inputContrasena)
        var bytesIn = pass.text.toString().toByteArray(Charsets.UTF_8)
        val messageDigest = MessageDigest.getInstance("SHA-512")
        val hashResult = messageDigest.digest(bytesIn)
        val passwordHash = hashResult.joinToString("") {
            "%02x".format(it)
        }
        if (!name.text.toString().equals("") && !surname1.text.toString().equals("") && !surname2.text.toString().equals("") && !username.text.toString().equals("") && !email.text.toString().equals("") && !pass.text.toString().equals("")) {
            startNextActivity(passwordHash)
        }
        else {
            Snackbar.make(findViewById<View>(android.R.id.content),"Error de registro",Snackbar.LENGTH_LONG)
                .show()
        }
    }
    private fun startNextActivity(password: String) {
        val i = Intent(this, codiVerificacioRegistre::class.java)

        i.putExtra("name",name.text.toString())
        i.putExtra("surname1",surname1.text.toString())
        i.putExtra("surname2",surname2.text.toString())
        i.putExtra("username",username.text.toString())
        i.putExtra("email",email.text.toString())
        i.putExtra("pass",password)

        startActivity(i)
    }
}