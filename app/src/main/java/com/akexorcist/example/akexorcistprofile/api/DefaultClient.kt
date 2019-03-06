package com.akexorcist.example.akexorcistprofile.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object DefaultClient {
    val client by lazy<OkHttpClient> {
        OkHttpClient().newBuilder().apply {
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        }.build()
    }
}