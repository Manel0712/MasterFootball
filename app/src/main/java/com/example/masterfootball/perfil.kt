package com.example.masterfootball

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import android.widget.Toast
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import java.io.InputStream
import java.io.OutputStream
import android.app.Activity
import android.content.ContentValues
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.masterfootball.classes.Users
import com.example.masterfootball.classes.bdConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.DriverManager

class perfil : AppCompatActivity() {
    private var imageBitmap: Bitmap? = null
    private var id: Int = 0
    lateinit var usernameText: TextView
    lateinit var pointsText: TextView
    lateinit var moneysText: TextView
    lateinit var gemsText: TextView
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            openGallery()
        } else {
            Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
        }
    }
    private val requestPermissionLauncher2 = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            configureProfile()
        } else {
            Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
        }
    }
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_perfil)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        id = intent.extras!!.getInt("userId")
        imageView = findViewById(R.id.imageView6)
        pointsText = findViewById(R.id.Points)
        moneysText = findViewById(R.id.Moneys)
        gemsText = findViewById(R.id.Gems)
        usernameText = findViewById(R.id.username)
        if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher2.launch(Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            configureProfile()
        }
        supportActionBar?.hide()
    }

    fun perfilImageChange(view: View) {
        responseDialogBasic()
    }

    fun responseDialogBasic() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Confirma enviament")
            .setCancelable(false)
            .setMessage("Un cop que hagis enviat l'intent, ja no el podras modificar\n\nEstas segur de que vols enviar l'intent?")
            .setNeutralButton("Camara") { dialog, which ->
                requestPermissions()
            }
            .setPositiveButton("Galeria") { dialog, which ->
                checkAndRequestPermissions()
            }
            .show()
    }

    private fun checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                openGallery()
            }
        } else {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                openGallery()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_SELECT_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            selectedImageUri?.let {
                cropImage(it)
            }
        }
    }

    private fun cropImage(imageUri: Uri) {
        try {
            val inputStream: InputStream = contentResolver.openInputStream(imageUri) ?: return
            val originalBitmap = BitmapFactory.decodeStream(inputStream)

            imageView.setImageBitmap(originalBitmap)
            saveProfileImage(imageUri.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val REQUEST_SELECT_IMAGE = 100
    }

    private fun requestPermissions() {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val storagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (cameraPermission == android.content.pm.PackageManager.PERMISSION_GRANTED &&
            storagePermission == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }
    }

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val photo: Bitmap? = result.data?.extras?.get("data") as Bitmap?
            if (photo != null) {
                imageBitmap = photo
                imageView.setImageBitmap(photo)
                imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                saveImageToGallery(photo)
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }

    private fun saveImageToGallery(bitmap: Bitmap) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "profile_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/ProfilePhotos")
        }

        val uri: Uri? = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        uri?.let {
            contentResolver.openOutputStream(it)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                Toast.makeText(this, "Foto guardada en galería", Toast.LENGTH_SHORT).show()
            } ?: Toast.makeText(this, "Error al guardar la foto", Toast.LENGTH_SHORT).show()
        } ?: Toast.makeText(this, "Error al crear el archivo", Toast.LENGTH_SHORT).show()
        saveProfileImage(uri.toString())
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            val cameraPermissionGranted = grantResults.getOrNull(0) == android.content.pm.PackageManager.PERMISSION_GRANTED
            //val storagePermissionGranted = grantResults.getOrNull(1) == android.content.pm.PackageManager.PERMISSION_GRANTED

            if (cameraPermissionGranted ) {  //&& storagePermissionGranted
                openCamera()
            } else {
                Toast.makeText(this, "Los permisos son necesarios para usar la cámara", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun saveProfileImage(URI: String) {
        CoroutineScope(Dispatchers.IO).launch {
            updateUser(URI, id)
        }
    }

    private fun updateUser(image: String, id: Int): Boolean {
        var dbconfiguration: bdConnection = bdConnection()
        var connection: Connection? = null
        return try {
            connection = DriverManager.getConnection(dbconfiguration.dbUrl, dbconfiguration.dbUser, dbconfiguration.dbPassword)

            val query = "UPDATE user SET profileImage = ? WHERE id_user = ?"
            val preparedStatement = connection.prepareStatement(query)

            preparedStatement.setString(1, image)
            preparedStatement.setInt(2, id)

            val rowsUpdated = preparedStatement.executeUpdate()

            rowsUpdated > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            connection?.close()
        }
    }

    private fun configureProfile() {
        CoroutineScope(Dispatchers.IO).launch {
            val isconfigurationSuccessful = configureProfileConnection()
            runOnUiThread {
                if (isconfigurationSuccessful?.profileImage != null) {
                    val imageUri: Uri = isconfigurationSuccessful?.profileImage!!.toUri()
                    val inputStream = contentResolver.openInputStream(imageUri)
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    imageView.setImageBitmap(bitmap)
                    imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                    inputStream?.close()
                }
                pointsText.text = "Punts " + isconfigurationSuccessful?.points.toString()
                moneysText.text = "Monedes " + isconfigurationSuccessful?.moneys.toString()
                gemsText.text = "Gemmes " + isconfigurationSuccessful?.moneys.toString()
                usernameText.text = isconfigurationSuccessful?.username.toString()
            }
        }
    }

    private fun configureProfileConnection(): Users? {
        var dbconfiguration: bdConnection = bdConnection()
        var connection: Connection? = null
        return try {
            connection = DriverManager.getConnection(dbconfiguration.dbUrl, dbconfiguration.dbUser, dbconfiguration.dbPassword)

            val query = "SELECT * FROM user WHERE id_user = ?"
            val preparedStatement = connection.prepareStatement(query)
            preparedStatement.setInt(1, id)

            val resultSet = preparedStatement.executeQuery()
            if (resultSet.next()) {
                val id = resultSet.getInt("id_user")
                val username = resultSet.getString("username")
                val email = resultSet.getString("email")
                val imageProfile = resultSet.getString("profileImage")
                val points = resultSet.getInt("points")
                val moneys = resultSet.getInt("moneys")
                val gems = resultSet.getInt("gems")

                Users(id, username, email, imageProfile, points, moneys, gems)
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
}