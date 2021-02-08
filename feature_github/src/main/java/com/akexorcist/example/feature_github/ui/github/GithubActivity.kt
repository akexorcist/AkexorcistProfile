package com.akexorcist.example.feature_github.ui.github

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.akexorcist.example.feature_github.api.GithubConfig
import com.akexorcist.example.feature_github.api.GithubManager
import com.akexorcist.example.feature_github.databinding.ActivityGithubBinding
import com.akexorcist.example.feature_github.util.converter.GithubConverter
import com.akexorcist.example.feature_github.vo.ui.Profile
import com.akexorcist.example.feature_github.vo.ui.Repo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubActivity : AppCompatActivity() {
    private val binding: ActivityGithubBinding by lazy {
        ActivityGithubBinding.inflate(layoutInflater)
    }
    private val adapter = GithubInfoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initRecyclerView()
        getProfile()
        showLoading()
    }

    private fun initRecyclerView() {
        adapter.setOnUrlClickListener { url -> openExternalWeb(url) }
        binding.githubRecyclerViewInfo.adapter = adapter
        binding.githubRecyclerViewInfo.layoutManager = LinearLayoutManager(this)
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
        binding.githubProgressBarContentLoading.show()
        binding.githubRecyclerViewInfo.visibility = View.INVISIBLE
        binding.githubTextViewEmptyResponse.visibility = View.INVISIBLE
        binding.githubTextViewServiceError.visibility = View.INVISIBLE
        TransitionManager.beginDelayedTransition(binding.root)
    }

    private fun showSuccess() {
        binding.githubProgressBarContentLoading.hide()
        binding.githubRecyclerViewInfo.visibility = View.VISIBLE
        binding.githubTextViewEmptyResponse.visibility = View.INVISIBLE
        binding.githubTextViewServiceError.visibility = View.INVISIBLE
        TransitionManager.beginDelayedTransition(binding.root)
    }

    private fun showEmpty() {
        binding.githubProgressBarContentLoading.hide()
        binding.githubRecyclerViewInfo.visibility = View.INVISIBLE
        binding.githubTextViewEmptyResponse.visibility = View.VISIBLE
        binding.githubTextViewServiceError.visibility = View.INVISIBLE
        TransitionManager.beginDelayedTransition(binding.root)
    }

    private fun showError() {
        binding.githubProgressBarContentLoading.hide()
        binding.githubRecyclerViewInfo.visibility = View.INVISIBLE
        binding.githubTextViewEmptyResponse.visibility = View.INVISIBLE
        binding.githubTextViewServiceError.visibility = View.VISIBLE
        TransitionManager.beginDelayedTransition(binding.root)
    }

    private fun openExternalWeb(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}
