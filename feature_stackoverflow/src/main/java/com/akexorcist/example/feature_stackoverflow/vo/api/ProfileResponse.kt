package com.akexorcist.example.feature_stackoverflow.vo.api

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("items") val profileList: List<Profile>?,
    @SerializedName("has_more") val hasMore: Boolean = false,
    @SerializedName("quota_max") val quotaMax: Int = 0,
    @SerializedName("quota_remaining") val quotaRemaining: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(Profile),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(profileList)
        parcel.writeByte(if (hasMore) 1 else 0)
        parcel.writeInt(quotaMax)
        parcel.writeInt(quotaRemaining)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProfileResponse> {
        override fun createFromParcel(parcel: Parcel): ProfileResponse {
            return ProfileResponse(parcel)
        }

        override fun newArray(size: Int): Array<ProfileResponse?> {
            return arrayOfNulls(size)
        }
    }
}