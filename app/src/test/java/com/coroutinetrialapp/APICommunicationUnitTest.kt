package com.coroutinetrialapp

import com.coroutinetrialapp.model.ApiInterface
import com.coroutinetrialapp.model.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

//API通信ができているかの単体テスト
class APICommunicationUnitTest {

    private lateinit var api:ApiInterface

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        api = ApiService.generate()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    @Throws(Exception::class)
    fun validateHaveWindSpeed() = runTest {

        //API通信で1週間の風速情報を取得
        val response = api.fetchWindSpeed()
        val windSpeedList = response.daily.windspeed_10m_max
        val timeList = response.daily.time

        assertNotNull(windSpeedList[0])
        assertNotNull(timeList[0])
        assertNotNull(windSpeedList[6])
        assertNotNull(timeList[6])
    }
}
