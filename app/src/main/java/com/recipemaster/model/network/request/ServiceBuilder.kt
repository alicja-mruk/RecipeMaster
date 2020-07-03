package com.recipemaster.model.network.request

import dagger.Module
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()
    private const val BASE_URL = "https://moodup.team/"


    @Singleton
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }

}