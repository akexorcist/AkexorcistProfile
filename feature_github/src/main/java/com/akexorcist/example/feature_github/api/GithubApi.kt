package com.akexorcist.example.feature_github.api

import com.akexorcist.example.feature_github.vo.api.Profile
import com.akexorcist.example.feature_github.vo.api.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("/users/{name}")
    fun getProfile(@Path("name") name: String): Call<Profile>

    @GET("/users/{name}/repos")
    fun getRepoList(
        @Path("name") name: String,
        @Query("sort") sort: String
    ): Call<List<Repo>>
}