package com.akexorcist.example.feature_github.ui.github

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akexorcist.example.feature_github.R
import com.akexorcist.example.feature_github.vo.ui.Profile
import com.akexorcist.example.feature_github.vo.ui.Repo

class GithubInfoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val TYPE_PROFILE = 0
        const val TYPE_TITLE = 1
        const val TYPE_REPO = 2
    }

    private var profile: Profile? = null
    private var repoList: List<Repo> = listOf()
    private var onUrlClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_PROFILE -> ProfileViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.view_holder_github_profile,
                    parent,
                    false
                )
            )
            TYPE_TITLE -> TitleViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.view_holder_github_title,
                    parent,
                    false
                )
            )
            TYPE_REPO -> RepoViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.view_holder_github_repo,
                    parent,
                    false
                )
            )
            else -> throw NullPointerException("View type ($viewType) not found")
        }

    override fun getItemViewType(position: Int): Int = when (position) {
        0 -> TYPE_PROFILE
        1 -> TYPE_TITLE
        else -> TYPE_REPO
    }

    override fun getItemCount(): Int = profile?.let {
        return repoList.size + 2
    } ?: run {
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProfileViewHolder -> {
                profile?.let {
                    setupProfileViewHolder(holder, it)
                }
            }
            is RepoViewHolder -> {
                setupRepoViewHolder(holder, repoList[position - 2])
            }
        }
    }

    fun setProfile(profile: Profile) {
        this.profile = profile
    }

    fun setRepoList(repoList: List<Repo>) {
        this.repoList = repoList
    }

    fun setOnUrlClickListener(listener: (String) -> Unit) {
        this.onUrlClickListener = listener
    }
    private fun setupProfileViewHolder(holder: ProfileViewHolder, item: Profile) {
        holder.setProfileImage(item.avatarUrl)
        holder.setName(item.name)
        holder.setCompany(item.company)
        holder.setLocation(item.location)
        holder.setRepoCount(item.publicRepos)
        holder.setGistCount(item.publicGists)
        holder.setFollowingCount(item.following)
        holder.setFollowerCount(item.followers)
        holder.setOnClickListener { item.url?.let { onUrlClickListener?.invoke(it) } }
    }

    private fun setupRepoViewHolder(holder: RepoViewHolder, item: Repo) {
        holder.setName(item.name)
        holder.setDescription(item.description)
        holder.setLanguage(item.language)
        holder.setLicense(item.license)
        holder.setOnClickListener { item.url?.let { onUrlClickListener?.invoke(it) } }
    }
}