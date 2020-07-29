package com.example.samplemvp.model

data class ProfileModel(
    val `data`: DataProfile
)

data class DataProfile(
    val address: String,
    val email: String,
    val firstName: String,
    val id: Int,
    val image: String,
    val lastName: String,
    val telephone: String,
    val userName: String
)