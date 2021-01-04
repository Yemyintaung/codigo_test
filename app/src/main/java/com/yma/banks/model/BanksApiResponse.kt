package com.yma.banks.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BanksApiResponse(
    @Expose @SerializedName("id")
    var id: Int,
    @Expose @SerializedName("name")
    var name: String,
    @Expose @SerializedName("description")
    var description: String?,
    @Expose @SerializedName("type")
    var type: String?,
    @Expose @SerializedName("maxAmount")
    var maxAmount: Double?,
    @Expose @SerializedName("length")
    var length: Int,
    @Expose @SerializedName("startColor")
    var startColor: String?,
    @Expose @SerializedName("centerColor")
    var centerColor: String?,
    @Expose @SerializedName("endColor")
    var endColor: String?,
    @Expose @SerializedName("bgColor")
    var bgColor: String?,
    @Expose @SerializedName("fontColor")
    var fontColor: String?,
    @Expose @SerializedName("logoUrl")
    var logoUrl: String?,
    @Expose @SerializedName("logoThumbnail")
    var logoThumbnail: String?,
    @Expose @SerializedName("instruction1")
    var instruction_1: String?,
    @Expose @SerializedName("instruction2")
    var instruction_2: String?,
    @Expose @SerializedName("enabled")
    var enabled: Int,
    @Expose @SerializedName("created_at")
    var createdAt: String?,
    @Expose @SerializedName("updated_at")
    var updatedAt: String?,
    @Expose @SerializedName("deleted_at")
    var deletedAt: String?,
    @Expose @SerializedName("is_major")
    var isMajor: Boolean,
    @Expose @SerializedName("logo_url")
    var logo_url: String?
) : Parcelable {
    constructor() : this(
        0,
        "",
        "",
        "",
        0.0,
        0,
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        0,
        "",
        "",
        "",
        false,
        ""
    )

}