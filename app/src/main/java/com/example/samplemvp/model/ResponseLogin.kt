package com.example.samplemvp.model

data class ResponseLogin(
    val `data`: Data,
    val result: Boolean
)

data class Data(
    val firstName: String,
    val id: Int,
    val lastName: String,
    val token: String
)
