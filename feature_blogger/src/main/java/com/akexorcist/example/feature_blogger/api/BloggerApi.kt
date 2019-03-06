package com.akexorcist.example.feature_blogger.api

import com.akexorcist.example.feature_blogger.vo.api.PostResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BloggerApi {
    @GET("/blogger/v3/blogs/{blog_id}/posts")
    fun getPostList(
        @Path("blog_id") blogId: String,
        @Query("key") key: String,
        @Query("fetchBodies") fetchBodies: Boolean = false,
        @Query("fetchImages") fetchImages: Boolean = false,
        @Query("maxResults") maxResults: Int = 30
    ): Call<PostResponse>
}