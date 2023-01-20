package com.coroutinetrialapp.model

import android.util.Log
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val api:ApiInterface){
    suspend fun fetchWindSpeed(): ApiResponse? {
        try {
            //レスポンス結果を取得
            return api.fetchWindSpeed()
        } catch (e: Exception) {
            Log.i("Error", e.toString())
        }
        return null
    }
}