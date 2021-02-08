package com.akexorcist.example.feature_stackoverflow.ui.stackoverflow

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.akexorcist.example.feature_stackoverflow.api.StackOverflowConfig
import com.akexorcist.example.feature_stackoverflow.api.StackOverflowManager
import com.akexorcist.example.feature_stackoverflow.databinding.ActivityStackoverflowBinding
import com.akexorcist.example.feature_stackoverflow.util.converter.StackOverflowConverter
import com.akexorcist.example.feature_stackoverflow.vo.api.ProfileResponse
import com.akexorcist.example.feature_stackoverflow.vo.api.Timeline
import com.akexorcist.example.feature_stackoverflow.vo.ui.Activity
import com.akexorcist.example.feature_stackoverflow.vo.ui.Profile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StackOverflowActivity : AppCompatActivity() {
    private val binding: ActivityStackoverflowBinding by lazy {
        ActivityStackoverflowBinding.inflate(layoutInflater)
    }
    private val adapter = StackOverflowInfoAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initRecyclerView()
        getProfile()
        showLoading()
    }

    private fun initRecyclerView() {
        adapter.setOnUrlClickListener { url -> openExternalWeb(url) }
        binding.stackoverflowRecyclerViewInfo.adapter = adapter
        binding.stackoverflowRecyclerViewInfo.layoutManager = LinearLayoutManager(this)
    }

    private fun getProfile() {
        StackOverflowManager.api.getProfile(StackOverflowConfig.USER_ID)
            .enqueue(object : Callback<ProfileResponse> {
                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    showError()
                }

                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    val result: ProfileResponse? = response.body()
                    result?.let {
                        updateProfile(StackOverflowConverter.profile(it))
                    } ?: run {
                        showError()
                    }
                }
            })
    }

    private fun getTimeline() {
        StackOverflowManager.api.getTimeline(StackOverflowConfig.USER_ID)
            .enqueue(object : Callback<Timeline> {
                override fun onFailure(call: Call<Timeline>, t: Throwable) {
                    showError()
                }

                override fun onResponse(
                    call: Call<Timeline>,
                    response: Response<Timeline>
                ) {
                    val result: Timeline? = response.body()
                    result?.let {
                        updateActivityList(StackOverflowConverter.timeline(it))
                        showSuccess()
                    } ?: run {
                        showError()
                    }
                }
            })
    }

    private fun updateProfile(profile: Profile) {
        adapter.setProfile(profile)
        getTimeline()
    }

    private fun updateActivityList(activityList: List<Activity>) {
        adapter.setActivityList(activityList)
        adapter.notifyDataSetChanged()
    }

    private fun showLoading() {
        binding.stackoverflowProgressBarContentLoading.show()
        binding.stackoverflowRecyclerViewInfo.visibility = View.INVISIBLE
        binding.stackoverflowTextViewEmptyResponse.visibility = View.INVISIBLE
        binding.stackoverflowTextViewServiceError.visibility = View.INVISIBLE
        TransitionManager.beginDelayedTransition(binding.root)
    }

    private fun showSuccess() {
        binding.stackoverflowProgressBarContentLoading.hide()
        binding.stackoverflowRecyclerViewInfo.visibility = View.VISIBLE
        binding.stackoverflowTextViewEmptyResponse.visibility = View.INVISIBLE
        binding.stackoverflowTextViewServiceError.visibility = View.INVISIBLE
        TransitionManager.beginDelayedTransition(binding.root)
    }

    private fun showEmpty() {
        binding.stackoverflowProgressBarContentLoading.hide()
        binding.stackoverflowRecyclerViewInfo.visibility = View.INVISIBLE
        binding.stackoverflowTextViewEmptyResponse.visibility = View.VISIBLE
        binding.stackoverflowTextViewServiceError.visibility = View.INVISIBLE
        TransitionManager.beginDelayedTransition(binding.root)
    }

    private fun showError() {
        binding.stackoverflowProgressBarContentLoading.hide()
        binding.stackoverflowRecyclerViewInfo.visibility = View.INVISIBLE
        binding.stackoverflowTextViewEmptyResponse.visibility = View.INVISIBLE
        binding.stackoverflowTextViewServiceError.visibility = View.VISIBLE
        TransitionManager.beginDelayedTransition(binding.root)
    }

    private fun openExternalWeb(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}
