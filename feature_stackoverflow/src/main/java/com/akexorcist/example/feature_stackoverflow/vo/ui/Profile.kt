package com.akexorcist.example.feature_stackoverflow.vo.ui

import android.os.Parcel
import android.os.Parcelable

data class Profile(
    val id: Long = -1,
    val name: String?,
    val image: String?,
    val url: String?,
    val location: String?,
    val reputation: Int = 0,
    val badge: Badge?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readParcelable(Badge::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeString(url)
        parcel.writeString(location)
        parcel.writeInt(reputation)
        parcel.writeParcelable(badge, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Profile> {
        override fun createFromParcel(parcel: Parcel): Profile {
            return Profile(parcel)
        }

        override fun newArray(size: Int): Array<Profile?> {
            return arrayOfNulls(size)
        }
    }
}