package com.example.samplemvp.model

data class Repodatabranchaftersearch(
    val `data`: List<Databranchafter>
)

data class Databranchafter(
    val id: Int,
    val name: String
)