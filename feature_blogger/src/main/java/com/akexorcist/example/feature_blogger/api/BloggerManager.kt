package com.akexorcist.example.feature_blogger.api

import com.akexorcist.example.akexorcistprofile.api.DefaultClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BloggerManager {
    val api: BloggerApi by lazy<BloggerApi> {
        val retrofit = Retrofit.Builder().apply {
            baseUrl(BloggerConfig.ENDPOINT)
            addConverterFactory(GsonConverterFactory.create())
            client(DefaultClient.client)
        }.build()
        retrofit.create(BloggerApi::class.java)
    }
}