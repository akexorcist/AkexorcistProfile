package com.akexorcist.example.feature_github.ui.github

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.akexorcist.example.feature_github.R
import com.akexorcist.example.feature_github.api.GithubConfig
import com.akexorcist.example.feature_github.api.GithubManager
import com.akexorcist.example.feature_github.util.converter.GithubConverter
import com.akexorcist.example.feature_github.vo.ui.Profile
import com.akexorcist.example.feature_github.vo.ui.Repo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_github.github_layout_root as layoutRoot
import kotlinx.android.synthetic.main.activity_github.github_progress_bar_content_loading as progressBarContentLoading
import kotlinx.android.synthetic.main.activity_github.github_recycler_view_info as recyclerViewProfile
import kotlinx.android.synthetic.main.activity_github.github_text_view_empty_response as textViewEmptyResponse
import kotlinx.android.synthetic.main.activity_github.github_text_view_service_error as textViewServiceError

class GithubActivity : AppCompatActivity() {
    private val adapter = GithubInfoAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_github)

        initRecyclerView()
        getProfile()
        showLoading()
    }

    private fun initRecyclerView() {
        adapter.setOnUrlClickListener { url -> openExternalWeb(url) }
        recyclerViewProfile.adapter = adapter
        recyclerViewProfile.layoutManager = LinearLayoutManager(this)
    }

    private fun getProfile() {
        GithubManager.api.getProfile(GithubConfig.USER)
            .enqueue(object : Callback<com.akexorcist.example.feature_github.vo.api.Profile> {
                override fun onFailure(call: Call<com.akexorcist.example.feature_github.vo.api.Profile>, t: Throwable) {
                    showError()
                }

                override fun onResponse(
                    call: Call<com.akexorcist.example.feature_github.vo.api.Profile>,
                    response: Response<com.akexorcist.example.feature_github.vo.api.Profile>
                ) {
                    val result: com.akexorcist.example.feature_github.vo.api.Profile? = response.body()
                    result?.let {
                        updateProfile(GithubConverter.profile(it))
                    } ?: run {
                        showError()
                    }
                }
            })
    }

    private fun getRepoList() {
        GithubManager.api.getRepoList(GithubConfig.USER, "pushed")
            .enqueue(object : Callback<List<com.akexorcist.example.feature_github.vo.api.Repo>> {
                override fun onFailure(call: Call<List<com.akexorcist.example.feature_github.vo.api.Repo>>, t: Throwable) {
                    showError()
                }

                override fun onResponse(
                    call: Call<List<com.akexorcist.example.feature_github.vo.api.Repo>>,
                    response: Response<List<com.akexorcist.example.feature_github.vo.api.Repo>>
                ) {
                    val result: List<com.akexorcist.example.feature_github.vo.api.Repo>? = response.body()
                    result?.let {
                        updateRepoList(GithubConverter.repo(it))
                        showSuccess()
                    } ?: run {
                        showError()
                    }
                }
            })
    }

    private fun updateProfile(profile: Profile) {
        adapter.setProfile(profile)
        getRepoList()
    }

    private fun updateRepoList(repoList: List<Repo>) {
        adapter.setRepoList(repoList)
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
