package com.akexorcist.example.feature_blogger.ui.blogger

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_holder_blogger_profile.blogger_image_view_profile as imageViewProfile
import kotlinx.android.synthetic.main.view_holder_blogger_profile.blogger_text_view_name as textViewName

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

    fun setOnClickListener(listener: () -> Unit) {
        itemView.setOnClickListener { listener() }
    }
}