package com.akexorcist.example.feature_stackoverflow.vo.ui

import android.os.Parcel
import android.os.Parcelable

data class Badge(
    val bronze: Int,
    val silver: Int,
    val gold: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(bronze)
        parcel.writeInt(silver)
        parcel.writeInt(gold)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Badge> {
        override fun createFromParcel(parcel: Parcel): Badge {
            return Badge(parcel)
        }

        override fun newArray(size: Int): Array<Badge?> {
            return arrayOfNulls(size)
        }
    }
}