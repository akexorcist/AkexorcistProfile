package com.akexorcist.example.feature_blogger.vo.api

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("kind") val kind: String?,
    @SerializedName("nextPageToken") val nextPageToken: String?,
    @SerializedName("items") val itemList: List<Post>?,
    @SerializedName("etag") val etag: String?
)