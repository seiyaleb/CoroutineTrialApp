package com.coroutinetrialapp.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService{
    //Retrofitインスタンスを生成
    private fun generateRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.open-meteo.com/")
            .build()
    }
    //APIインターフェイスの実体を生成
    fun generate():ApiInterface {
        return generateRetrofit().create(ApiInterface::class.java)
    }
}