package com.example.masterfootball

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.masterfootball.classes.bdConnection
import com.example.masterfootball.classes.email
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.sql.Connection
import java.sql.DriverManager
import kotlin.random.Random

class codiVerificacioRegistre : AppCompatActivity() {
    lateinit var name: String
    lateinit var surname1: String
    lateinit var surname2: String
    lateinit var username: String
    lateinit var userEmail: String
    lateinit var pass: String
    lateinit var verificationCode: TextInputEditText
    var random: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_codi_verificacio_registre)
        supportActionBar?.hide()
        name = intent.extras!!.getString("name").toString()
        surname1 = intent.extras!!.getString("surname1").toString()
        surname2 = intent.extras!!.getString("surname2").toString()
        username = intent.extras!!.getString("username").toString()
        userEmail = intent.extras!!.getString("email").toString()
        pass = intent.extras!!.getString("pass").toString()
        enviarEmail()
    }
    private fun registreConnection(): Boolean {
        var dbconfiguration: bdConnection = bdConnection()
        var connection: Connection? = null
        return try {
            connection = DriverManager.getConnection(dbconfiguration.dbUrl, dbconfiguration.dbUser, dbconfiguration.dbPassword)

            val query = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?)"
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setInt(1, 0)
            preparedStatement.setString(2, name)
            preparedStatement.setString(3, surname1)
            preparedStatement.setString(4, surname2)
            preparedStatement.setString(5, username)
            preparedStatement.setString(6, userEmail)
            preparedStatement.setString(7, pass)

            val resultSet = preparedStatement.executeUpdate()
            resultSet > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            connection?.close()
        }
    }
    fun enviarEmail() {
        val email = email("maneldelahozrodriguez@gmail.com", "dumg azqj necj nxtq")
        val destinatario = userEmail
        val asunto = "Verificacion de registro"
        random = Random.nextInt(100000, 1000000)
        val mensaje = "Introduce el siguiente codigo para verificar el registro\n${random}"
        Thread {
            email.sendEmail(destinatario, asunto, mensaje)
        }.start()
    }

    fun verificarLogin(view: View) {
        verificationCode = findViewById<TextInputEditText?>(R.id.textInputEditText3)
        var code = verificationCode.text.toString().toInt()
        if (code==random) {
            registreConnection()
            CoroutineScope(Dispatchers.IO).launch {
                val isLoginSuccessful = registreConnection()
                runOnUiThread {
                    if (isLoginSuccessful != null) {
                        startNextActivity()
                    }
                }
            }
        }
        else {
            Snackbar.make(findViewById<View>(android.R.id.content),"Codigo incorrecto", Snackbar.LENGTH_LONG)
                .show()
        }
    }
    private fun startNextActivity() {
        val i = Intent(this, Loggin::class.java)

        startActivity(i)
    }
}