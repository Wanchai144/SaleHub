package com.example.samplemvp.model

data class ResponseCategories(
    val `data`: List<DataOrderList>,
    val links: Links,
    val meta: Meta,
    val result: Boolean
)

data class DataOrderList(
    val id: Int,
    val image: String,
    val name: String,
    val categoryName:String
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