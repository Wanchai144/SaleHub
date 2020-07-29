package com.example.samplemvp.model

data class ResponseDetailProducts(
    val `data`: Data,
    val result: Boolean
){

data class Data(
    val categoryId: Int,
    val categoryName: String,
    val detail: String,
    val id: Int,
    val image: String,
    val imageProduct: List<ImageProduct>,
    val name: String,
    val price: Int,
    val productCode: String,
    val seriesId: Int,
    val seriesName: String
){
    data class ImageProduct(
        val id: Int,
        val image: String
    )
}

}