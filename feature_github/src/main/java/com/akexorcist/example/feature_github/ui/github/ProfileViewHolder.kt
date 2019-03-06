package com.akexorcist.example.feature_github.ui.github

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_holder_github_profile.github_image_view_profile as imageViewProfile
import kotlinx.android.synthetic.main.view_holder_github_profile.github_text_view_company as textViewCompany
import kotlinx.android.synthetic.main.view_holder_github_profile.github_text_view_follower_count as textViewFollowerCount
import kotlinx.android.synthetic.main.view_holder_github_profile.github_text_view_following_count as textViewFollowingCount
import kotlinx.android.synthetic.main.view_holder_github_profile.github_text_view_gist_count as textViewGistCount
import kotlinx.android.synthetic.main.view_holder_github_profile.github_text_view_location as textViewLocation
import kotlinx.android.synthetic.main.view_holder_github_profile.github_text_view_name as textViewName
import kotlinx.android.synthetic.main.view_holder_github_profile.github_text_view_repository_count as textViewRepoCount

class ProfileViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    fun setProfileImage(url: String?) {
        url?.let {
            Glide.with(itemView.context).load(url).into(imageViewProfile)
        }
    }

    fun setName(name: String?) {
        name?.let {
            textViewName.text = name
        } ?: run {
            textViewName.text = ""
        }
    }

    fun setCompany(company: String?) {
        company?.let {
            textViewCompany.text = company
            textViewCompany.visibility = View.VISIBLE
        } ?: run {
            textViewCompany.text = ""
            textViewCompany.visibility = View.INVISIBLE
        }
    }

    fun setLocation(location: String?) {
        location?.let {
            textViewLocation.text = location
            textViewLocation.visibility = View.VISIBLE
        } ?: run {
            textViewLocation.text = ""
            textViewLocation.visibility = View.INVISIBLE
        }
    }

    fun setRepoCount(count: Int) {
        textViewRepoCount.text = count.toString()
    }

    fun setGistCount(count: Int) {
        textViewGistCount.text = count.toString()
    }

    fun setFollowerCount(count: Int) {
        textViewFollowerCount.text = count.toString()
    }

    fun setFollowingCount(count: Int) {
        textViewFollowingCount.text = count.toString()
    }

    fun setOnClickListener(listener: () -> Unit) {
        itemView.setOnClickListener { listener() }
    }
}