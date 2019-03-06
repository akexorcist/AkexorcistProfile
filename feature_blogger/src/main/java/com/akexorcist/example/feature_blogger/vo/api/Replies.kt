package com.akexorcist.example.feature_blogger.vo.api

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Replies(
    @SerializedName("totalItems") val totalItems: String?,
    @SerializedName("selfLink") val selfLink: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(totalItems)
        parcel.writeString(selfLink)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Replies> {
        override fun createFromParcel(parcel: Parcel): Replies {
            return Replies(parcel)
        }

        override fun newArray(size: Int): Array<Replies?> {
            return arrayOfNulls(size)
        }
    }
}