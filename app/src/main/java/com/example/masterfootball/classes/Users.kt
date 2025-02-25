package com.example.masterfootball.classes

data class Users (
    val id: Int,
    val username: String,
    val email: String,
    val profileImage: String? = "",
    val points: Int? = 0,
    val moneys: Int? = 0,
    val gems: Int? = 0
)