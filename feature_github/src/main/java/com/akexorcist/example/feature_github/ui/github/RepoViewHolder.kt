package com.akexorcist.example.feature_github.ui.github

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.akexorcist.example.feature_github.databinding.ViewHolderGithubRepoBinding

class RepoViewHolder(
    private val binding: ViewHolderGithubRepoBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun setName(name: String?) {
        name?.let {
            binding.githubTextViewName.text = it
        } ?: run {
            binding.githubTextViewName.text = ""
        }
    }

    fun setDescription(description: String?) {
        description?.let {
            binding.githubTextViewDescription.text = it
        } ?: run {
            binding.githubTextViewDescription.text = ""
        }
    }

    fun setLanguage(language: String?) {
        language?.let {
            binding.githubTextViewLanguage.text = it
            binding.githubTextViewLanguage.visibility = View.VISIBLE
        } ?: run {
            binding.githubTextViewLanguage.text = ""
            binding.githubTextViewLanguage.visibility = View.GONE
        }
    }

    fun setLicense(license: String?) {
        license?.let {
            binding.githubTextViewLicense.text = it
            binding.githubTextViewLicense.visibility = View.VISIBLE
        } ?: run {
            binding.githubTextViewLicense.text = ""
            binding.githubTextViewLicense.visibility = View.GONE
        }
    }

    fun setOnClickListener(listener: () -> Unit) {
        itemView.setOnClickListener { listener() }
    }
}
