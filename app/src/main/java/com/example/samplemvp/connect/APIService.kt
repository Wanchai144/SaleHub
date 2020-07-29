package com.example.samplemvp.connect

import com.example.samplemvp.model.*
import io.reactivex.Observable
import retrofit2.http.*

interface APIService {

    @POST("api/loginuser")
    fun doLogin(@Body body: BodyLogin?): Observable<ResponseLogin>

    @GET("api/categories")
    fun getCategories(
        @Header("Authorization") accessToken: String
    ): Observable<ResponseCategories>


    @FormUrlEncoded
    @POST("api/draftordersdetail")
    fun getorderlist(
        @Header("Authorization") accessToken: String,
        @Field("id") seriesId: Int
    ): Observable<drafdetailModel>



    @GET("api/orders/{id}")
    fun getIDorderlist(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int
    ): Observable<Responseorderslist>


    @GET("api/orders")
    fun getorder(
        @Header("Authorization") accessToken: String
    ): Observable<Reponseapi>

    @GET("api/profile")
    fun getprofile(
        @Header("Authorization") accessToken: String
    ): Observable<ProfileModel>



    @DELETE("api/draftorders/{id}")
    fun delectdraforder(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int
    ): Observable<DelectProfileModel>


    @GET("api/orders/{id}")
    fun getOrderList(
        @Header("Authorization") accessToken: String,
        @Path("id") id: Int
    ): Observable<Responseorderslist>

    @GET("api/draftorders")
    fun getListBook(
        @Header("Authorization") accessToken: String
    ): Observable<ResponseListBook>


    @FormUrlEncoded
    @POST("api/serieslist")
    fun doProductlist(
        @Header("Authorization") accessToken: String,
        @Field("id") id: String
    ): Observable<ResponsSeriesList>

    @FormUrlEncoded
    @POST("api/listproducts")
    fun doProductlists(
        @Header("Authorization") accessToken: String,
        @Field("category_id") category_id: Int,
        @Field("series_id") series_id: Int
    ): Observable<Responsesubcategory>

    @FormUrlEncoded
    @POST("api/detailproducts")
    fun doDetailProducts(
        @Header("Authorization") accessToken: String,
        @Field("id") id: Int
    ): Observable<ResponseDetailProducts>

    @FormUrlEncoded
    @POST("api/draftorders")
    fun doInserBasker(
        @Header("Authorization") accessToken: String,
        @FieldMap map:HashMap<String, String>
    ): Observable<ResponseBasket>


    @FormUrlEncoded
    @POST("api/orders")
    fun doInserorder(
        @Header("Authorization") accessToken: String,
        @FieldMap map:HashMap<String, String>
    ): Observable<Repobuyorder>

    @FormUrlEncoded
    @POST("api/listsearchcustomerautocomplete")
    fun onSearchOrto(
        @Header("Authorization") accessToken: String,
        @Field ("keyword") keyword:String
    ): Observable<Respoonsearch>

    @FormUrlEncoded
    @POST("api/databranchaftersearch?")
    fun onDatabranchaftersearch(
        @Header("Authorization") accessToken: String,
        @Field ("id") id:Int
    ): Observable<Repodatabranchaftersearch>


    @FormUrlEncoded
    @POST("api/searchcustomer")
    fun onSearchData(
        @Header("Authorization") accessToken: String,
        @Field ("id") id:Int
    ): Observable<RespoOnDataSearch>
}