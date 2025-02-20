package com.example.masterfootball.classes

import com.example.masterfootball.classes.Player
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import okhttp3.OkHttpClient
import okhttp3.Request
import com.example.masterfootball.classes.Result
import kotlinx.serialization.json.Json
import org.json.JSONObject
import com.example.masterfootball.classes.Welcome
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class APIRequests {
    var playersList : MutableList<Result>? = ArrayList()
    suspend fun getBarcelonaPlayers():MutableList<Result>? = withContext(Dispatchers.IO) {
        val apiKey = "0ddb4b77bb9207f0226e1250d92b2b9615ff2495461bf306a5f47eaed57d1726"
        val url = "https://apiv2.allsportsapi.com/football/?&met=Teams&teamId=97&APIkey=$apiKey"
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .get()
            .build()
        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    println("Error en la solicitud: ${response.code}")
                    return@withContext null
                } else {
                    // Se obtiene la respuesta en formato String
                    val responseBody = response.body?.string() ?: "Respuesta vacía"

                    // Se deserializa el JSON en un objeto `Welcome`
                    val welcome = Json.decodeFromString<Welcome>(responseBody)

                    // Convierte `welcome.result` en una lista mutable
                    playersList = welcome.result.toMutableList()
                }
            }
            return@withContext playersList
        } catch (e: Exception) {
            // Manejo de errores si ocurre alguna excepción
            println("Error al realizar la solicitud: ${e.localizedMessage}")
            null
        }
        null
    }
}