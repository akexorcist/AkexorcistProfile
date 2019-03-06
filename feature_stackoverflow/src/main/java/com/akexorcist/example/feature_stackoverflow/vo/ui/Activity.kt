package com.akexorcist.example.feature_stackoverflow.vo.ui

data class Activity(
    val timelineType: String?,
    val postType: String?,
    val date: Long,
    val title: String?,
    val detail: String?
)