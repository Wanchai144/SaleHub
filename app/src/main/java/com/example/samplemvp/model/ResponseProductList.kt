package com.example.samplemvp.model

data class ResponseProductList(
    val `data`: List<Data>,
    val links: Links,
    val meta: Meta,
    val result: Boolean
){

data class Data(
    val categoryId: Int,
    val categoryName: String,
    val id: Int,
    val image: String,
    val name: String,
    val price: String,
    val productCode: String,
    val seriesId: Int,
    val seriesName: String
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
)}