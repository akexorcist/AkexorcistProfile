package com.akexorcist.example.feature_stackoverflow.ui.stackoverflow

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.akexorcist.example.feature_stackoverflow.R
import com.akexorcist.example.feature_stackoverflow.api.StackOverflowConfig
import com.akexorcist.example.feature_stackoverflow.api.StackOverflowManager
import com.akexorcist.example.feature_stackoverflow.util.converter.StackOverflowConverter
import com.akexorcist.example.feature_stackoverflow.vo.api.ProfileResponse
import com.akexorcist.example.feature_stackoverflow.vo.api.Timeline
import com.akexorcist.example.feature_stackoverflow.vo.ui.Activity
import com.akexorcist.example.feature_stackoverflow.vo.ui.Profile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_stackoverflow.stackoverflow_layout_root as layoutRoot
import kotlinx.android.synthetic.main.activity_stackoverflow.stackoverflow_progress_bar_content_loading as progressBarContentLoading
import kotlinx.android.synthetic.main.activity_stackoverflow.stackoverflow_recycler_view_info as recyclerViewProfile
import kotlinx.android.synthetic.main.activity_stackoverflow.stackoverflow_text_view_empty_response as textViewEmptyResponse
import kotlinx.android.synthetic.main.activity_stackoverflow.stackoverflow_text_view_service_error as textViewServiceError

class StackOverflowActivity : AppCompatActivity() {
    private val adapter = StackOverflowInfoAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stackoverflow)

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
