package com.example.masterfootball.classes

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection
import java.sql.DriverManager

class updatePointsANDMoneys {
    suspend fun updatePointsANDMoneys(points: Int, moneys: Int, gems: Int, user: Int) = withContext(Dispatchers.IO) {
        var activeUser = recuperatePointsANDMoneys(user)
        var newPoints = points + activeUser?.points!!.toInt()
        var newMoneys = moneys + activeUser?.moneys!!.toInt()
        var newGems = gems + activeUser?.gems!!.toInt()
        updateUser(newPoints, newMoneys, newGems, user)
    }

    fun recuperatePointsANDMoneys(id: Int): Users? {
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

                if (points == null && moneys == null && gems == null) {
                    Users(id, username, email, imageProfile)
                }
                else {
                    Users(id, username, email, imageProfile, points, moneys, gems)
                }
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

    private fun updateUser(points: Int, moneys: Int, gems: Int, id: Int): Boolean {
        var dbconfiguration: bdConnection = bdConnection()
        var connection: Connection? = null
        return try {
            connection = DriverManager.getConnection(dbconfiguration.dbUrl, dbconfiguration.dbUser, dbconfiguration.dbPassword)

            val query = "UPDATE user SET points = ?, moneys = ?, gems = ? WHERE id_user = ?"
            val preparedStatement = connection.prepareStatement(query)

            preparedStatement.setInt(1, points)
            preparedStatement.setInt(2, moneys)
            preparedStatement.setInt(3, gems)
            preparedStatement.setInt(4, id)

            val rowsUpdated = preparedStatement.executeUpdate()

            rowsUpdated > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            connection?.close()
        }
    }
}