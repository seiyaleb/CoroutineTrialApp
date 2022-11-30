package com.coroutinetrialapp.model

import retrofit2.http.GET

//APIインターフェイスを定義
interface ApiInterface {
    @GET("v1/forecast?latitude=35.6785&longitude=139.6823&daily=windspeed_10m_max&timezone=Asia%2FTokyo")
    suspend fun fetchWindSpeed(): ApiResponse
}