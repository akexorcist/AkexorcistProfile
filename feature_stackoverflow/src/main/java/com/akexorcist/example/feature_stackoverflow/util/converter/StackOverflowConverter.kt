package com.akexorcist.example.feature_stackoverflow.util.converter

import com.akexorcist.example.feature_stackoverflow.vo.api.ProfileResponse
import com.akexorcist.example.feature_stackoverflow.vo.api.Timeline
import com.akexorcist.example.feature_stackoverflow.vo.ui.Activity
import com.akexorcist.example.feature_stackoverflow.vo.ui.Badge
import com.akexorcist.example.feature_stackoverflow.vo.ui.Profile

object StackOverflowConverter {
    fun profile(response: ProfileResponse): Profile =
        if (response.profileList?.isNotEmpty() == true) {
            val profile = response.profileList.get(0)
            val badge = Badge(
                bronze = profile.badge?.bronze ?: 0,
                silver = profile.badge?.silver ?: 0,
                gold = profile.badge?.gold ?: 0
            )
            Profile(
                id = profile.userId,
                name = profile.displayName,
                image = profile.profileImage,
                url = profile.link,
                location = profile.location,
                reputation = profile.reputation,
                badge = badge
            )
        } else {
            Profile(
                name = null,
                badge = null,
                url = null,
                image = null,
                location = null
            )
        }

    fun timeline(timeline: Timeline): List<Activity> =
        timeline.itemList?.let { items ->
            return if (items.isNotEmpty()) {
                items.map { item ->
                    Activity(
                        timelineType = item.timelineType,
                        postType = item.postType,
                        date = item.creationDate,
                        title = item.title,
                        detail = item.detail
                    )
                }
            } else {
                listOf()
            }
        } ?: run {
            return listOf()
        }
}