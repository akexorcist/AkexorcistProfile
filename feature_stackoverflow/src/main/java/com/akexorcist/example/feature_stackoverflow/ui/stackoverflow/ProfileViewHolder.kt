package com.akexorcist.example.feature_stackoverflow.ui.stackoverflow

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.akexorcist.example.feature_stackoverflow.R
import com.akexorcist.example.feature_stackoverflow.databinding.ViewHolderStackoverflowProfileBinding
import com.bumptech.glide.Glide

class ProfileViewHolder(
    private val binding: ViewHolderStackoverflowProfileBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun setProfileImage(url: String?) {
        url?.let {
            Glide.with(itemView.context).load(url).into(binding.stackoverflowImageViewProfile)
        } ?: run {
            binding.stackoverflowImageViewProfile.setImageBitmap(null)
        }
    }

    fun setName(name: String?) {
        name?.let {
            binding.stackoverflowTextViewName.text = name
        } ?: run {
            binding.stackoverflowTextViewName.text = ""
        }
    }

    fun setLocation(location: String?) {
        location?.let {
            binding.stackoverflowTextViewLocation.text = location
            binding.stackoverflowTextViewLocation.visibility = View.VISIBLE
        } ?: run {
            binding.stackoverflowTextViewLocation.text = ""
            binding.stackoverflowTextViewLocation.visibility = View.INVISIBLE
        }
    }

    fun setReputation(reputation: Int) {
        binding.stackoverflowTextViewReputation.text =
            itemView.resources.getString(R.string.reputations, reputation)
    }

    fun setGoldCount(count: Int) {
        binding.stackoverflowTextViewGoldCount.text = count.toString()
    }

    fun setSilverCount(count: Int) {
        binding.stackoverflowTextViewSilverCount.text = count.toString()
    }

    fun setBronzeCount(count: Int) {
        binding.stackoverflowTextViewBronzeCount.text = count.toString()
    }

    fun setOnClickListener(listener: () -> Unit) {
        itemView.setOnClickListener { listener() }
    }
}