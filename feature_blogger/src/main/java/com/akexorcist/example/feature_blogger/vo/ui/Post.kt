package com.akexorcist.example.feature_blogger.vo.ui

import android.os.Parcel
import android.os.Parcelable

data class Post(
    val id: String?,
    val title: String?,
    val url: String?,
    val published: String?,
    val updated: String?,
    val authorName: String?,
    val authorUrl: String?,
    val authorImage: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(published)
        parcel.writeString(updated)
        parcel.writeString(authorName)
        parcel.writeString(authorUrl)
        parcel.writeString(authorImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}