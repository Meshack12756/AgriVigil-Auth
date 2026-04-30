package com.example.agrivigil_auth

data class Farmer(
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = "",
    val photoUrl: String = "",
    val farmInfo: FarmInfo = FarmInfo()
)
