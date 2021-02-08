package com.akexorcist.example.feature_blogger.ui.blogger

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.akexorcist.example.feature_blogger.api.BloggerConfig
import com.akexorcist.example.feature_blogger.api.BloggerManager
import com.akexorcist.example.feature_blogger.databinding.ActivityBloggerBinding
import com.akexorcist.example.feature_blogger.util.converter.BloggerConverter
import com.akexorcist.example.feature_blogger.vo.api.PostResponse
import com.akexorcist.example.feature_blogger.vo.ui.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BloggerActivity : AppCompatActivity() {
    private val binding: ActivityBloggerBinding by lazy {
        ActivityBloggerBinding.inflate(layoutInflater)
    }
    private val adapter = BloggerInfoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initRecyclerView()
        getPostList()
        showLoading()
    }

    private fun initRecyclerView() {
        adapter.setOnUrlClickListener { url -> openExternalWeb(url) }
        binding.bloggerRecyclerViewInfo.adapter = adapter
        binding.bloggerRecyclerViewInfo.layoutManager = LinearLayoutManager(this)
    }

    private fun getPostList() {
        BloggerManager.api.getPostList(
            blogId = BloggerConfig.BLOG_ID,
            key = BloggerConfig.API_KEY
        ).enqueue(object : Callback<PostResponse> {
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                showError()
            }

            override fun onResponse(
                call: Call<PostResponse>,
                response: Response<PostResponse>
            ) {
                val result: PostResponse? = response.body()
                result?.let {
                    val postList = BloggerConverter.post(it)
                    if (postList.isNotEmpty()) {
                        updatePostList(postList)
                        showSuccess()
                    } else {
                        showEmpty()
                    }
                } ?: run {
                    showError()
                }
            }
        })
    }

    private fun updatePostList(postList: List<Post>) {
        adapter.setPostList(postList)
        adapter.notifyDataSetChanged()
    }

    private fun showLoading() {
        binding.bloggerProgressBarContentLoading.show()
        binding.bloggerRecyclerViewInfo.visibility = View.INVISIBLE
        binding.bloggerTextViewEmptyResponse.visibility = View.INVISIBLE
        binding.bloggerTextViewServiceError.visibility = View.INVISIBLE
        TransitionManager.beginDelayedTransition(binding.root)
    }

    private fun showSuccess() {
        binding.bloggerProgressBarContentLoading.hide()
        binding.bloggerRecyclerViewInfo.visibility = View.VISIBLE
        binding.bloggerTextViewEmptyResponse.visibility = View.INVISIBLE
        binding.bloggerTextViewServiceError.visibility = View.INVISIBLE
        TransitionManager.beginDelayedTransition(binding.root)
    }

    private fun showEmpty() {
        binding.bloggerProgressBarContentLoading.hide()
        binding.bloggerRecyclerViewInfo.visibility = View.INVISIBLE
        binding.bloggerTextViewEmptyResponse.visibility = View.VISIBLE
        binding.bloggerTextViewServiceError.visibility = View.INVISIBLE
        TransitionManager.beginDelayedTransition(binding.root)
    }

    private fun showError() {
        binding.bloggerProgressBarContentLoading.hide()
        binding.bloggerRecyclerViewInfo.visibility = View.INVISIBLE
        binding.bloggerTextViewEmptyResponse.visibility = View.INVISIBLE
        binding.bloggerTextViewServiceError.visibility = View.VISIBLE
        TransitionManager.beginDelayedTransition(binding.root)
    }

    private fun openExternalWeb(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}
