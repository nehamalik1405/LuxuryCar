package com.a.luxurycar.code_files.ui.home.model.advertiser_response


import com.google.gson.annotations.SerializedName

data class AdvertiserWithUsResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
)