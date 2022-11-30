package com.coroutinetrialapp.model

import android.util.Log
import java.lang.Exception

class MainRepository {
    private val api = ApiService.generate()
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