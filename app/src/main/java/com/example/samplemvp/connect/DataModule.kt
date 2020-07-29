package com.example.samplemvp.connect


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class DataModule {

    companion object {
        private var retrofit: Retrofit? = null
        private var gson: Gson? = null
        private val BASE_URL = "http://demo.salehub.wewillapp.support/"

        @Synchronized
        private fun getInstance(): Retrofit? {
            if (retrofit == null) {
                if (gson == null) {
                    gson = GsonBuilder().setLenient().create()
                }
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson!!))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            }
            return retrofit
        }

        private var apiService: APIService? = null

        fun getUserApiInstance(): APIService? {
            if (apiService == null) apiService =
                getInstance()!!.create(APIService::class.java)
            return apiService
        }
    }

}