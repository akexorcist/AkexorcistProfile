package com.akexorcist.example.feature_github.util.converter

import com.akexorcist.example.feature_github.vo.ui.Profile
import com.akexorcist.example.feature_github.vo.ui.Repo

object GithubConverter {
    fun profile(profile: com.akexorcist.example.feature_github.vo.api.Profile): Profile = Profile(
        id = profile.id,
        url = profile.htmlUrl,
        avatarUrl = profile.avatarUrl,
        name = profile.name,
        company = profile.company,
        blog = profile.blog,
        location = profile.location,
        bio = profile.bio,
        publicRepos = profile.publicRepos,
        publicGists = profile.publicGists,
        followers = profile.followers,
        following = profile.following
    )

    fun repo(repoList: List<com.akexorcist.example.feature_github.vo.api.Repo>): List<Repo> {
        return repoList.map {
            Repo(
                id = it.id,
                name = it.name,
                fullName = it.fullName,
                owner = it.owner,
                description = it.description,
                fork = it.fork,
                updatedAt = it.updatedAt,
                language = it.language,
                license = it.license?.name,
                url = it.htmlUrl
            )
        }
    }
}