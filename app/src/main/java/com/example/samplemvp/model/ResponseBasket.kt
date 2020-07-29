package com.example.samplemvp.model

data class ResponseBasket(
   val `data`: DataBasket,
val result: Boolean
)
data class DataBasket(
    val date: String,
    val id: Int,
    val name: String,
    val time: String
)
