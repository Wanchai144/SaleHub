package com.example.samplemvp.model

data class RespoOnDataSearch(
    val `data`: DataorderSearch ,
    val result: Boolean
)

data class DataorderSearch(
    val address: String,
    val amphure: String,
    val district: String,
    val firstname: String,
    val id: Int? = 0,
    val lastname: String,
    val moo: String,
    val postcode: String,
    val province: String,
    val road: String,
    val soi: String,
    val taxPayerNumber: Any,
    val telephone: String
)