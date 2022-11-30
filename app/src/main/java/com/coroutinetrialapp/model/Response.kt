package com.coroutinetrialapp.model

//以下でレスポンス結果で受け取る
data class ApiResponse(val daily: DailyResponse)
data class DailyResponse(val time:List<String>,val windspeed_10m_max:List<Double>)