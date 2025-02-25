package com.example.masterfootball.classes

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager

class profileImageConfigure {
    suspend fun profileConfigure(user: Int):String = withContext(Dispatchers.IO) {
        var userProfileData = profileImage(user)
        return@withContext userProfileData?.profileImage.toString()
    }
    fun profileImage(id: Int): Users? {
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