package com.example.samplemvp.model

data class Reponseapi(
    val `data`: List<DataGetid>,
    val links: Link,
    val meta: Met,
    val result: Boolean
)

data class DataGetid(
    val date: String,
    val id: Int,
    val name: String,
    val orderStatus: String,
    val status: Int,
    val time: String
)

data class Link(
    val first: String,
    val last: String,
    val next: String,
    val prev: Any
)

data class Met(
    val current_page: Int,
    val from: Int,
    val last_page: Int,
    val path: String,
    val per_page: Int,
    val to: Int,
    val total: Int
)