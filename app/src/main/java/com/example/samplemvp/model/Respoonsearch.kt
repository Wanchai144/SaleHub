package com.example.samplemvp.model

class Respoonsearch (
        val message: String?="",
        val data: List<DataSearchs>,
        val result: Boolean
    )

    data class DataSearchs(
        val firstname: String,
        val id: Int,
        val lastname: String
    )
