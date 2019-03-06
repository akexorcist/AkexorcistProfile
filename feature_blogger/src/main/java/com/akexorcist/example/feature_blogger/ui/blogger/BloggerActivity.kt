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
import com.akexorcist.example.feature_blogger.util.converter.BloggerConverter
import com.akexorcist.example.feature_blogger.vo.api.PostResponse
import com.akexorcist.example.feature_blogger.vo.ui.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_blogger.blogger_layout_root as layoutRoot
import kotlinx.android.synthetic.main.activity_blogger.blogger_progress_bar_content_loading as progressBarContentLoading
import kotlinx.android.synthetic.main.activity_blogger.blogger_recycler_view_info as recyclerViewProfile
import kotlinx.android.synthetic.main.activity_blogger.blogger_text_view_empty_response as textViewEmptyResponse
import kotlinx.android.synthetic.main.activity_blogger.blogger_text_view_service_error as textViewServiceError


class BloggerActivity : AppCompatActivity() {
    private val adapter = BloggerInfoAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.akexorcist.example.feature_blogger.R.layout.activity_blogger)

        initRecyclerView()
        getPostList()
        showLoading()
    }

    private fun initRecyclerView() {
        adapter.setOnUrlClickListener { url -> openExternalWeb(url) }
        recyclerViewProfile.adapter = adapter
        recyclerViewProfile.layoutManager = LinearLayoutManager(this)
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
        progressBarContentLoading.show()
        recyclerViewProfile.visibility = View.INVISIBLE
        textViewEmptyResponse.visibility = View.INVISIBLE
        textViewServiceError.visibility = View.INVISIBLE
        TransitionManager.beginDelayedTransition(layoutRoot)
    }

    private fun showSuccess() {
        progressBarContentLoading.hide()
        recyclerViewProfile.visibility = View.VISIBLE
        textViewEmptyResponse.visibility = View.INVISIBLE
        textViewServiceError.visibility = View.INVISIBLE
        TransitionManager.beginDelayedTransition(layoutRoot)
    }

    private fun showEmpty() {
        progressBarContentLoading.hide()
        recyclerViewProfile.visibility = View.INVISIBLE
        textViewEmptyResponse.visibility = View.VISIBLE
        textViewServiceError.visibility = View.INVISIBLE
        TransitionManager.beginDelayedTransition(layoutRoot)
    }

    private fun showError() {
        progressBarContentLoading.hide()
        recyclerViewProfile.visibility = View.INVISIBLE
        textViewEmptyResponse.visibility = View.INVISIBLE
        textViewServiceError.visibility = View.VISIBLE
        TransitionManager.beginDelayedTransition(layoutRoot)
    }

    private fun openExternalWeb(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}
