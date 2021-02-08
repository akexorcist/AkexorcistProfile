package com.akexorcist.example.feature_blogger.ui.blogger

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akexorcist.example.feature_blogger.databinding.ViewHolderBloggerPostBinding
import com.akexorcist.example.feature_blogger.databinding.ViewHolderBloggerProfileBinding
import com.akexorcist.example.feature_blogger.databinding.ViewHolderBloggerTitleBinding
import com.akexorcist.example.feature_blogger.vo.ui.Post

class BloggerInfoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_PROFILE = 0
        const val TYPE_TITLE = 1
        const val TYPE_POST = 2
    }

    private var postList: List<Post> = listOf()
    private var onUrlClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_PROFILE -> ProfileViewHolder(
                ViewHolderBloggerProfileBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            TYPE_TITLE -> TitleViewHolder(
                ViewHolderBloggerTitleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            TYPE_POST -> PostViewHolder(
                ViewHolderBloggerPostBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw NullPointerException("View type ($viewType) not found")
        }

    override fun getItemViewType(position: Int): Int = when (position) {
        0 -> TYPE_PROFILE
        1 -> TYPE_TITLE
        else -> TYPE_POST
    }

    override fun getItemCount(): Int = if (postList.isNotEmpty()) postList.size + 2 else 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProfileViewHolder -> {
                setupProfileViewHolder(holder, postList[0])
            }
            is PostViewHolder -> {
                setupPostViewHolder(holder, postList[position - 2])
            }
        }
    }

    fun setPostList(postList: List<Post>) {
        this.postList = postList
    }

    fun setOnUrlClickListener(listener: (String) -> Unit) {
        this.onUrlClickListener = listener
    }

    private fun setupProfileViewHolder(holder: ProfileViewHolder, item: Post) {
        item.authorImage?.let { url ->
            holder.setProfileImage("https:${url.replace("/s35-c/", "/s300-c/")}")
        }
        holder.setName(item.authorName)
        holder.setOnClickListener { item.authorUrl?.let { onUrlClickListener?.invoke(it) } }
    }

    private fun setupPostViewHolder(holder: PostViewHolder, item: Post) {
        holder.setTitle(item.title)
        holder.setUpdatedDate(item.updated)
        holder.setOnClickListener { item.url?.let { onUrlClickListener?.invoke(it) } }
    }
}