package com.akexorcist.example.feature_github.vo.ui

import android.os.Parcel
import android.os.Parcelable
import com.akexorcist.example.feature_github.vo.api.Profile

data class Repo(
    val id: Long = -1,
    val name: String?,
    val fullName: String?,
    val owner: Profile?,
    val description: String?,
    val fork: Boolean = false,
    val updatedAt: String?,
    val language: String?,
    val license: String?,
    val url: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Profile::class.java.classLoader),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(fullName)
        parcel.writeParcelable(owner, flags)
        parcel.writeString(description)
        parcel.writeByte(if (fork) 1 else 0)
        parcel.writeString(updatedAt)
        parcel.writeString(language)
        parcel.writeString(license)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Repo> {
        override fun createFromParcel(parcel: Parcel): Repo {
            return Repo(parcel)
        }

        override fun newArray(size: Int): Array<Repo?> {
            return arrayOfNulls(size)
        }
    }
}