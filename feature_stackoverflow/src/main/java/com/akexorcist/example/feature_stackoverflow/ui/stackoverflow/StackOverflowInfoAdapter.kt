package com.akexorcist.example.feature_stackoverflow.ui.stackoverflow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akexorcist.example.feature_stackoverflow.R
import com.akexorcist.example.feature_stackoverflow.vo.ui.Activity
import com.akexorcist.example.feature_stackoverflow.vo.ui.Profile

class StackOverflowInfoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_PROFILE = 0
        const val TYPE_TITLE = 1
        const val TYPE_ACTIVITY = 2
        const val TYPE_UNKNOWN = 3
    }

    private var profile: Profile? = null
    private var activityList: List<Activity> = listOf()
    private var onUrlClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_PROFILE -> ProfileViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.view_holder_stackoverflow_profile,
                    parent,
                    false
                )
            )
            TYPE_TITLE -> TitleViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.view_holder_stackoverflow_title,
                    parent,
                    false
                )
            )
            TYPE_ACTIVITY -> ActivityViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.view_holder_stackoverflow_activity,
                    parent,
                    false
                )
            )
            else -> throw NullPointerException("View type ($viewType) not found")
        }

    override fun getItemViewType(position: Int): Int =
        if (profile != null && activityList.isNotEmpty()) when (position) {
            0 -> TYPE_PROFILE
            1 -> TYPE_TITLE
            else -> TYPE_ACTIVITY
        }
        else if (profile != null)
            TYPE_PROFILE
        else if (activityList.isNotEmpty()) when (position) {
            0 -> TYPE_TITLE
            else -> TYPE_ACTIVITY
        }
        else TYPE_UNKNOWN

    override fun getItemCount(): Int =
        if (profile != null && activityList.isNotEmpty()) activityList.size + 2
        else if (profile != null) 1
        else if (activityList.isNotEmpty()) activityList.size + 1
        else 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProfileViewHolder -> {
                profile?.let {
                    setupProfileViewHolder(holder, it)
                }
            }
            is ActivityViewHolder -> {
                setupActivityViewHolder(holder, activityList[position - 2])
            }
        }
    }

    fun setProfile(profile: Profile) {
        this.profile = profile
    }

    fun setActivityList(activityList: List<Activity>) {
        this.activityList = activityList
    }

    fun setOnUrlClickListener(listener: (String) -> Unit) {
        this.onUrlClickListener = listener
    }

    private fun setupProfileViewHolder(holder: ProfileViewHolder, item: Profile) {
        holder.setProfileImage(item.image)
        holder.setName(item.name)
        holder.setLocation(item.location)
        holder.setReputation(item.reputation)
        holder.setBronzeCount(item.badge?.bronze ?: 0)
        holder.setSilverCount(item.badge?.silver ?: 0)
        holder.setGoldCount(item.badge?.gold ?: 0)
        holder.setOnClickListener { item.url?.let { onUrlClickListener?.invoke(it) } }
    }

    private fun setupActivityViewHolder(holder: ActivityViewHolder, item: Activity) {
        holder.setTitle(if (item.title != null) item.title else item.detail)
        holder.setType(item.timelineType)
        holder.setDate(item.date)
    }
}