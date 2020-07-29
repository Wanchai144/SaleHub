package com.example.samplemvp.model

data class Repobuyorder(
    val `data`: Databuyorder,
    val result: Boolean
)

data class Databuyorder(
    val date: String,
    val id: Int,
    val name: String,
    val orderStatus: String,
    val status: Int,
    val time: String
)