package com.yma.banks.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PersonApiResponseContainer(
    @Expose @SerializedName("id")
    var code: Int,
    @Expose @SerializedName("name")
    var message: String,
    @Expose @SerializedName("data")
    var data: List<PersonApiResponse>
)