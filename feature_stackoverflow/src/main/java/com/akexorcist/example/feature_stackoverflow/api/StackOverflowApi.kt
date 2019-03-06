package com.akexorcist.example.feature_stackoverflow.api

import com.akexorcist.example.feature_stackoverflow.vo.api.ProfileResponse
import com.akexorcist.example.feature_stackoverflow.vo.api.Timeline
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface StackOverflowApi {
    @GET("/2.2/users/{userId}?site=stackoverflow")
    fun getProfile(@Path("userId") userId: String): Call<ProfileResponse>

    @GET("/2.2/users/{userId}/timeline?site=stackoverflow")
    fun getTimeline(@Path("userId") userId: String): Call<Timeline>
}