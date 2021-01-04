package com.yma.banks.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonApiResponse(
    @Expose @SerializedName("id")
    var id: String,
    @Expose @SerializedName("lastName")
    var lastName: String,
    @Expose @SerializedName("firstName")
    var firstName: String,
    @Expose @SerializedName("email")
    var email: String,
    @Expose @SerializedName("title")
    var title: String,
    @Expose @SerializedName("picture")
    var picture: String
) : Parcelable {
    constructor() : this(
        "",
        "",
        "",
        "",
        "",
        ""
    )

}