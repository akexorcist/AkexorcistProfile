package com.akexorcist.example.feature_stackoverflow.vo.api

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Activity(
    @SerializedName("post_id") val postId: Long,
    @SerializedName("user_id") val userId: Long,
    @SerializedName("comment_id") val commentId: Long,
    @SerializedName("badge_id") val badgeId: Long,
    @SerializedName("timeline_type") val timelineType: String?,
    @SerializedName("post_type") val postType: String?,
    @SerializedName("creation_date") val creationDate: Long,
    @SerializedName("title") val title: String?,
    @SerializedName("detail") val detail: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(postId)
        parcel.writeLong(userId)
        parcel.writeLong(commentId)
        parcel.writeLong(badgeId)
        parcel.writeString(timelineType)
        parcel.writeString(postType)
        parcel.writeLong(creationDate)
        parcel.writeString(title)
        parcel.writeString(detail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Activity> {
        override fun createFromParcel(parcel: Parcel): Activity {
            return Activity(parcel)
        }

        override fun newArray(size: Int): Array<Activity?> {
            return arrayOfNulls(size)
        }
    }
}