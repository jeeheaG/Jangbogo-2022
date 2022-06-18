package com.example.jangbogo_2022.network

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyApplication: Application() {
    companion object{
        var networkService: NetworkService

        val retrofit: Retrofit
            get() = Retrofit.Builder()
                .baseUrl("http://openapi.seoul.go.kr:8088/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        init {
            networkService = retrofit.create(NetworkService::class.java)
        }

    }
}