package com.akexorcist.example.feature_github.ui.github

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.akexorcist.example.feature_github.databinding.ViewHolderGithubProfileBinding
import com.bumptech.glide.Glide

class ProfileViewHolder(
    private val binding: ViewHolderGithubProfileBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun setProfileImage(url: String?) {
        url?.let {
            Glide.with(itemView.context).load(url).into(binding.githubImageViewProfile)
        }
    }

    fun setName(name: String?) {
        name?.let {
            binding.githubTextViewName.text = name
        } ?: run {
            binding.githubTextViewName.text = ""
        }
    }

    fun setCompany(company: String?) {
        company?.let {
            binding.githubTextViewCompany.text = company
            binding.githubTextViewCompany.visibility = View.VISIBLE
        } ?: run {
            binding.githubTextViewCompany.text = ""
            binding.githubTextViewCompany.visibility = View.INVISIBLE
        }
    }

    fun setLocation(location: String?) {
        location?.let {
            binding.githubTextViewLocation.text = location
            binding.githubTextViewLocation.visibility = View.VISIBLE
        } ?: run {
            binding.githubTextViewLocation.text = ""
            binding.githubTextViewLocation.visibility = View.INVISIBLE
        }
    }

    fun setRepoCount(count: Int) {
        binding.githubTextViewRepositoryCount.text = count.toString()
    }

    fun setGistCount(count: Int) {
        binding.githubTextViewGistCount.text = count.toString()
    }

    fun setFollowerCount(count: Int) {
        binding.githubTextViewFollowerCount.text = count.toString()
    }

    fun setFollowingCount(count: Int) {
        binding.githubTextViewFollowingCount.text = count.toString()
    }

    fun setOnClickListener(listener: () -> Unit) {
        itemView.setOnClickListener { listener() }
    }
}
