package com.akexorcist.example.feature_github.api

import com.akexorcist.example.akexorcistprofile.api.DefaultClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubManager {
    val api: GithubApi by lazy<GithubApi> {
        val retrofit = Retrofit.Builder().apply {
            baseUrl(GithubConfig.ENDPOINT)
            addConverterFactory(GsonConverterFactory.create())
            client(DefaultClient.client)
        }.build()
        retrofit.create(GithubApi::class.java)
    }
}