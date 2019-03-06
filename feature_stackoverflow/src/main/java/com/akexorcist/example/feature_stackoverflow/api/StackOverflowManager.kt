package com.akexorcist.example.feature_stackoverflow.api

import com.akexorcist.example.akexorcistprofile.api.DefaultClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object StackOverflowManager {
    val api: StackOverflowApi by lazy<StackOverflowApi> {
        val retrofit = Retrofit.Builder().apply {
            baseUrl(StackOverflowConfig.ENDPOINT)
            addConverterFactory(GsonConverterFactory.create())
            client(DefaultClient.client)
        }.build()
        retrofit.create(StackOverflowApi::class.java)
    }
}