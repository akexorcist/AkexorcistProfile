package com.akexorcist.example.feature_stackoverflow.vo.api

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("badge_counts") val badge: BadgeCount?,
    @SerializedName("account_id") val accountId: Long = -1,
    @SerializedName("is_employee") val isEmployee: Boolean = false,
    @SerializedName("last_modified_date") val lastModifiedDate: Long = 0,
    @SerializedName("last_access_date") val lastAccessDate: Long = 0,
    @SerializedName("reputation_change_year") val reputationChangeYear: Int = 0,
    @SerializedName("reputation_change_quarter") val reputationChangeQuarter: Int = 0,
    @SerializedName("reputation_change_month") val reputationChangeMonth: Int = 0,
    @SerializedName("reputation_change_week") val reputationChangeWeek: Int = 0,
    @SerializedName("reputation_change_day") val reputationChangeDay: Int = 0,
    @SerializedName("reputation") val reputation: Int = 0,
    @SerializedName("creation_date") val creationDate: Long = 0,
    @SerializedName("user_type") val userType: String?,
    @SerializedName("user_id") val userId: Long = -1,
    @SerializedName("location") val location: String?,
    @SerializedName("website_url") val websiteUrl: String?,
    @SerializedName("link") val link: String?,
    @SerializedName("profile_image") val profileImage: String?,
    @SerializedName("display_name") val displayName: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(BadgeCount::class.java.classLoader),
        parcel.readLong(),
        parcel.readByte() != 0.toByte(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(badge, flags)
        parcel.writeLong(accountId)
        parcel.writeByte(if (isEmployee) 1 else 0)
        parcel.writeLong(lastModifiedDate)
        parcel.writeLong(lastAccessDate)
        parcel.writeInt(reputationChangeYear)
        parcel.writeInt(reputationChangeQuarter)
        parcel.writeInt(reputationChangeMonth)
        parcel.writeInt(reputationChangeWeek)
        parcel.writeInt(reputationChangeDay)
        parcel.writeInt(reputation)
        parcel.writeLong(creationDate)
        parcel.writeString(userType)
        parcel.writeLong(userId)
        parcel.writeString(location)
        parcel.writeString(websiteUrl)
        parcel.writeString(link)
        parcel.writeString(profileImage)
        parcel.writeString(displayName)
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