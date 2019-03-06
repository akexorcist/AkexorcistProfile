package com.akexorcist.example.feature_stackoverflow.vo.api

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class BadgeCount(
    @SerializedName("bronze") val bronze: Int = 0,
    @SerializedName("silver") val silver: Int = 0,
    @SerializedName("gold") val gold: Int = 0
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

    companion object CREATOR : Parcelable.Creator<BadgeCount> {
        override fun createFromParcel(parcel: Parcel): BadgeCount {
            return BadgeCount(parcel)
        }

        override fun newArray(size: Int): Array<BadgeCount?> {
            return arrayOfNulls(size)
        }
    }
}