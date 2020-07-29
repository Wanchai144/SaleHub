package com.example.samplemvp.realm

import com.example.samplemvp.model.BasketModel
import io.realm.Realm
import io.realm.Realm.deleteRealm
import io.realm.RealmConfiguration
import io.realm.RealmResults


class BasketRealmManager {

    val realm: Realm by lazy {
        val config = RealmConfiguration.Builder()
            .name("basketSaleHubKabaS.realm")
            .schemaVersion(10)
            .build()
        Realm.getInstance(config)
    }

    fun find(id: Int): BasketModel? {
        return realm.where(BasketModel::class.java).equalTo("id", id).findFirst()
    }


    fun findall(): List<BasketModel> {
        return realm.where(BasketModel::class.java).findAll()
    }

    fun sumprie(): Double {
        return realm.where<BasketModel>(BasketModel::class.java).sum("sumPrieProducts").toDouble()
    }

    fun sumProduct(): Int {
        return realm.where<BasketModel>(BasketModel::class.java).sum("sumProducts").toInt()
    }

//    fun sumPersenVat(sumPersenVat:String) : BasketModel? {
//        return realm.where(BasketModel::class.java).equalTo("sumPersenVat",sumPersenVat).toString()
//    }

    fun Productcode(idProduct: String): BasketModel? {
        return realm.where(BasketModel::class.java).equalTo("Productcode", idProduct).findFirst()
    }

    fun idProduct(idProduct: String): BasketModel? {
        return realm.where(BasketModel::class.java).equalTo("idProduct", idProduct).findFirst()
    }



        fun insertBasket(
    idProduct: String,
    imageProduct : String,
    name: String
    , prieProducts: Int
    , sumProducts: Int
    , sumPrieProducts: Int
        ,Persen:Int
    ) {
        realm.beginTransaction()

        val newId: Int

        val currentIdNum = realm.where<BasketModel>(BasketModel::class.java).max("id")

        newId = if (currentIdNum == null) {
            1
        } else {
            currentIdNum.toInt() + 1
        }
        val Basket = realm.createObject(BasketModel::class.java, newId)
        Basket.idproduct = idProduct
        Basket.nameProduct = name
        Basket.prieProducts = prieProducts
        Basket.sumProducts = sumProducts
        Basket.imageProduct = imageProduct
        Basket.sumPrieProducts = sumPrieProducts
            Basket.Persen = Persen
        realm.commitTransaction()
    }



    fun insert(
        idProduct: String,
        Productcode: String
        , prieProducts: Int
        , sumProducts: Int
        , imageProduct: String
        , sumPrieProducts: Int
        , name: String
    ) {
        realm.beginTransaction()

        val newId: Int

        val currentIdNum = realm.where<BasketModel>(BasketModel::class.java).max("id")

        newId = if (currentIdNum == null) {
            1
        } else {
            currentIdNum.toInt() + 1
        }
        val Basket = realm.createObject(BasketModel::class.java, newId)
        Basket.idproduct = idProduct
        Basket.Productcode = Productcode
        Basket.prieProducts = prieProducts
        Basket.sumProducts = sumProducts
        Basket.imageProduct = imageProduct
        Basket.sumPrieProducts = sumPrieProducts
        Basket.nameProduct = name
        realm.commitTransaction()

    }

    fun update(id: Int, sumPrieProducts: Int, sumProducts: Int) {
        realm.beginTransaction()
        val Basket = find(id)
        Basket?.sumPrieProducts = sumPrieProducts
        Basket?.sumProducts = sumProducts
        realm.commitTransaction()
    }

    //
    fun updatePersenVat(id: Int, edtpersen: String, edtvat:String,tv_sumpersens: String, tv_sumvat:String) {
        realm.beginTransaction()
        val Basket = find(id)
        Basket?.edtpersen = edtpersen
        Basket?.edtvat = edtvat
        Basket?.tv_sumpersens = tv_sumpersens
        Basket?.tv_sumvat = tv_sumvat
        realm.commitTransaction()
    }

    fun updateDicount(id: Int, dicount: Int) {
        realm.beginTransaction()
        val Basket = find(id)
        Basket?.discount = dicount
        realm.commitTransaction()

    }

    fun delectall() {
        val results: RealmResults<BasketModel> =
            realm.where<BasketModel>(BasketModel::class.java).findAll()
        realm.beginTransaction()
        results.deleteAllFromRealm()
        realm.commitTransaction()
    }

    fun updatDrafBatken(id: Int, sumProducts: Int, sumPrieProducts: Int) {
        realm.beginTransaction()
        val Basket = find(id)
        Basket?.sumProducts = sumProducts
        Basket?.sumPrieProducts = sumPrieProducts
        realm.commitTransaction()
    }

    fun updateMain(id: Int, sumProducts: Int, sumPrieProducts: Int) {
        realm.beginTransaction()
        val Basket = find(id)
        Basket?.sumProducts = sumProducts
        Basket?.sumPrieProducts = sumPrieProducts
        realm.commitTransaction()
    }

    fun deletwById(id: Int) {
        realm.beginTransaction()
        var results = realm.where(BasketModel::class.java).equalTo("id", id).findAll()
        results.deleteAllFromRealm()
        realm.commitTransaction()
    }

    fun deletall() {


    }

}