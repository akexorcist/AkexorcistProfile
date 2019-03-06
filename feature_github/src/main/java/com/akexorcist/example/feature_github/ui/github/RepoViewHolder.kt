package com.akexorcist.example.feature_github.ui.github

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_holder_github_repo.github_text_view_description as textViewDescription
import kotlinx.android.synthetic.main.view_holder_github_repo.github_text_view_language as textViewLanguage
import kotlinx.android.synthetic.main.view_holder_github_repo.github_text_view_license as textViewLicense
import kotlinx.android.synthetic.main.view_holder_github_repo.github_text_view_name as textViewName

class RepoViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {
    fun setName(name: String?) {
        name?.let {
            textViewName.text = it
        } ?: run {
            textViewName.text = ""
        }
    }

    fun setDescription(description: String?) {
        description?.let {
            textViewDescription.text = it
        } ?: run {
            textViewDescription.text = ""
        }
    }

    fun setLanguage(language: String?) {
        language?.let {
            textViewLanguage.text = it
            textViewLanguage.visibility = View.VISIBLE
        } ?: run {
            textViewLanguage.text = ""
            textViewLanguage.visibility = View.GONE
        }
    }

    fun setLicense(license: String?) {
        license?.let {
            textViewLicense.text = it
            textViewLicense.visibility = View.VISIBLE
        } ?: run {
            textViewLicense.text = ""
            textViewLicense.visibility = View.GONE
        }
    }

    fun setOnClickListener(listener: () -> Unit) {
        itemView.setOnClickListener { listener() }
    }
}