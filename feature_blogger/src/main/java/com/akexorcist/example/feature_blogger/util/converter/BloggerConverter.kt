package com.akexorcist.example.feature_blogger.util.converter

import com.akexorcist.example.feature_blogger.vo.api.PostResponse
import com.akexorcist.example.feature_blogger.vo.ui.Post

object BloggerConverter {
    fun post(response: PostResponse): List<Post> {
        response.itemList?.let { postList ->
            return postList.map { post ->
                Post(
                    id = post.id,
                    published = post.published,
                    updated = post.updated,
                    url = post.url,
                    title = post.title,
                    authorName = post.author?.displayName,
                    authorUrl = post.author?.url,
                    authorImage = post.author?.image?.url
                )
            }
        } ?: run {
            return listOf()
        }
    }
}