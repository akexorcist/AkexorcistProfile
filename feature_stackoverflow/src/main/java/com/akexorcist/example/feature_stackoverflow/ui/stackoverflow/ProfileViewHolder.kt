package com.akexorcist.example.feature_stackoverflow.ui.stackoverflow

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.akexorcist.example.feature_stackoverflow.R
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_holder_stackoverflow_profile.stackoverflow_image_view_profile as imageViewProfile
import kotlinx.android.synthetic.main.view_holder_stackoverflow_profile.stackoverflow_text_view_bronze_count as textViewBronzeCount
import kotlinx.android.synthetic.main.view_holder_stackoverflow_profile.stackoverflow_text_view_gold_count as textViewGoldCount
import kotlinx.android.synthetic.main.view_holder_stackoverflow_profile.stackoverflow_text_view_location as textViewLocation
import kotlinx.android.synthetic.main.view_holder_stackoverflow_profile.stackoverflow_text_view_name as textViewName
import kotlinx.android.synthetic.main.view_holder_stackoverflow_profile.stackoverflow_text_view_reputation as textViewReputation
import kotlinx.android.synthetic.main.view_holder_stackoverflow_profile.stackoverflow_text_view_silver_count as textViewSilverCount

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

    fun setLocation(location: String?) {
        location?.let {
            textViewLocation.text = location
            textViewLocation.visibility = View.VISIBLE
        } ?: run {
            textViewLocation.text = ""
            textViewLocation.visibility = View.INVISIBLE
        }
    }

    fun setReputation(reputation: Int) {
        textViewReputation.text = itemView.resources.getString(R.string.reputations, reputation)
    }

    fun setGoldCount(count: Int) {
        textViewGoldCount.text = count.toString()
    }

    fun setSilverCount(count: Int) {
        textViewSilverCount.text = count.toString()
    }

    fun setBronzeCount(count: Int) {
        textViewBronzeCount.text = count.toString()
    }

    fun setOnClickListener(listener: () -> Unit) {
        itemView.setOnClickListener { listener() }
    }
}