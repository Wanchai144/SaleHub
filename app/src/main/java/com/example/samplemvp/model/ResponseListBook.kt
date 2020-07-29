package com.example.samplemvp.model

data class ResponseListBook(
    val `data`: List<DataListBook>,
    val links: Links,
    val meta: Meta,
    val result: Boolean,
    val sumOrder: Int
){
    data class DataListBook(
        val date: String,
        val id: Int,
        val name: String,
        val time: String
    )

    data class Links(
        val first: String,
        val last: String,
        val next: Any,
        val prev: Any
    )

    data class Meta(
        val current_page: Int,
        val from: Int,
        val last_page: Int,
        val path: String,
        val per_page: Int,
        val to: Int,
        val total: Int
    )
}

