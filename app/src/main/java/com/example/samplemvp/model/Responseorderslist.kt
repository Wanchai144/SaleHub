package com.example.samplemvp.model

data class Responseorderslist(
    val `data`: Dataorder,
    val result: Boolean
) { data class Dataorder(
        val bill: String,
        val branch: String,
        val customerAddress: String,
        val customerAmphure: String,
        val customerDistrict: String,
        val customerFirstName: String,
        val customerLastName: String,
        val customerMoo: String,
        val customerPostcode: String,
        val customerProvince: String,
        val customerRoad: String,
        val customerSoi: String,
        val customerTelephone: String,
        val customerTexNumber: String,
        val date: String,
        val discount: Int,
        val draftOrderId: Any,
        val id: Int,
        val name: String,
        val nameBill: String,
        val net: Double,
        val noteOrder: String,
        val orderInvoce: String,
        val orderStatus: String,
        val price: Double,
        val priceDiscount: Double,
        val priceVat: Double,
        val product: List<Product>,
        val status: Int,
        val time: String,
        val trackingNumber: String,
        val vat: Int
    )
{
    data class Product(
        val id: Int,
        val productAmount: Int,
        val productId: Int,
        val productImage: String,
        val productName: String,
        val productPercent: Double,
        val productPrice: Double,
        val productPriceTotal: Double
    )
}

}


