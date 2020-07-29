package com.example.samplemvp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

 open class BasketModel :RealmObject() {

    @PrimaryKey
    var id : Int = 1
    var idproduct : String = ""
    var Productcode : String = ""
    var prieProducts : Int = 0
    var sumProducts : Int = 0
    var imageProduct : String = ""
    var sumPrieProducts : Int = 0
    var nameProduct : String = ""
    var edtpersen : String = "0"
    var edtvat : String = "0"
    var tv_sumpersens : String = ""
    var tv_sumvat : String = ""
    var discount : Int? = 0
    var Persen : Int = 0




}