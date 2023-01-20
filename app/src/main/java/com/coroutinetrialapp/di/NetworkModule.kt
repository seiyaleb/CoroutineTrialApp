package com.coroutinetrialapp.di

import com.coroutinetrialapp.model.ApiInterface
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    //Gsonインスタンスを生成
    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    //Retrofitインスタンスを生成
    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://api.open-meteo.com/")
            .build()
    }

    //APIインターフェイスの実体を生成
    @Singleton
    @Provides
    fun providePostApi(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)
}