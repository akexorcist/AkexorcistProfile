package com.akexorcist.example.feature_blogger.vo.api

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("kind") val kind: String?,
    @SerializedName("id") val id: String?,
    @SerializedName("blog") val blog: Blog?,
    @SerializedName("published") val published: String?,
    @SerializedName("updated") val updated: String?,
    @SerializedName("etag") val etag: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("selfLink") val selfLink: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("author") val author: Author?,
    @SerializedName("replies") val replies: Replies?,
    @SerializedName("labels") val labels: List<String?>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Blog::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Author::class.java.classLoader),
        parcel.readParcelable(Replies::class.java.classLoader),
        parcel.createStringArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(kind)
        parcel.writeString(id)
        parcel.writeParcelable(blog, flags)
        parcel.writeString(published)
        parcel.writeString(updated)
        parcel.writeString(etag)
        parcel.writeString(url)
        parcel.writeString(selfLink)
        parcel.writeString(title)
        parcel.writeParcelable(author, flags)
        parcel.writeParcelable(replies, flags)
        parcel.writeStringList(labels)
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