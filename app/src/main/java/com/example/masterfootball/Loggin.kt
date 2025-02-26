package com.example.masterfootball

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import android.view.View
import android.widget.Toast
import java.security.MessageDigest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.DriverManager
import com.example.masterfootball.classes.bdConnection
import com.example.masterfootball.classes.Users
import com.google.android.material.snackbar.Snackbar

class Loggin : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 100
    lateinit var inputUser: TextInputEditText
    lateinit var inputPass: TextInputEditText
    lateinit var userEmail: String
    var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.loggin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()
        inputUser = findViewById(R.id.inputLogginUsuari)
        inputPass = findViewById(R.id.inputLogginContrasena)
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ (API 33): Necesita READ_MEDIA_IMAGES
            if (checkSelfPermission(android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES), PERMISSION_REQUEST_CODE)
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android 6 a Android 12 (API 23 - 32): Necesita READ_EXTERNAL_STORAGE
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun login(view: View) {
        var password = inputPass.text.toString()
        var bytesIn = password.toByteArray(Charsets.UTF_8)
        val messageDigest = MessageDigest.getInstance("SHA-512")
        val hashResult = messageDigest.digest(bytesIn)
        val passwordHash = hashResult.joinToString("") {
            "%02x".format(it)
        }
        CoroutineScope(Dispatchers.IO).launch {
            val isLoginSuccessful = loginConnection(inputUser.text!!.toString(), passwordHash)
            runOnUiThread {
                if (isLoginSuccessful != null) {
                    userEmail = isLoginSuccessful.email
                    userId = isLoginSuccessful.id
                    startNextActivity()
                } else {
                    Snackbar.make(findViewById<View>(android.R.id.content),"Usuario o contraseña incorrectos.",
                        Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
    fun registre(view: View) {
        val i = Intent(this, registre::class.java)

        startActivity(i)
    }
    private fun loginConnection(username: String, password: String): Users? {
        var dbconfiguration: bdConnection = bdConnection()
        var connection: Connection? = null
        return try {
            connection = DriverManager.getConnection(dbconfiguration.dbUrl, dbconfiguration.dbUser, dbconfiguration.dbPassword)

            val query = "SELECT * FROM user WHERE username = ? AND password = ?"
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setString(1, username)
            preparedStatement.setString(2, password)

            val resultSet = preparedStatement.executeQuery()
            if (resultSet.next()) {
                val id = resultSet.getInt("id_user")
                val username = resultSet.getString("username")
                val email = resultSet.getString("email")

                Users(id, username, email)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            connection?.close()
        }
    }
    private fun startNextActivity() {
        val i = Intent(this, verificacioLoggin::class.java)

        i.putExtra("email",userEmail)
        i.putExtra("userId",userId)

        startActivity(i)
    }
    fun startGuies(view: View) {
        val i = Intent(this, Guies::class.java)

        startActivity(i)
    }
}