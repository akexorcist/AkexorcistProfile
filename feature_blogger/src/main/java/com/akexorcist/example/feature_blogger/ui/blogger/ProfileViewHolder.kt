package com.akexorcist.example.feature_blogger.ui.blogger

import androidx.recyclerview.widget.RecyclerView
import com.akexorcist.example.feature_blogger.databinding.ViewHolderBloggerProfileBinding
import com.bumptech.glide.Glide

class ProfileViewHolder(
    private val binding: ViewHolderBloggerProfileBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun setProfileImage(url: String?) {
        url?.let {
            Glide.with(itemView.context).load(url).into(binding.bloggerImageViewProfile)
        }
    }

    fun setName(name: String?) {
        name?.let {
            binding.bloggerTextViewName.text = name
        } ?: run {
            binding.bloggerTextViewName.text = ""
        }
    }

    fun setOnClickListener(listener: () -> Unit) {
        itemView.setOnClickListener { listener() }
    }
}
