package com.example.samplemvp.model

data class drafdetailModel(
    val `data`: Drafdetail,
    val result: Boolean
){
    data class Drafdetail(
        val date: String,
        val discount: Int,
        val id: Int,
        val name: String,
        val net: Int,
        val price: Int,
        val priceDiscount: Int,
        val priceVat: Int,
        val product: List<Product>,
        val time: String,
        val vat: Int
    )
{
    data class Product(
        val id: Int,
        val productAmount: Int,
        val productId: Int,
        val productImage: String,
        val productName: String,
        val productPercent: Int,
        val productPrice: Int,
        val productPriceTotal: Int
    )
}

}