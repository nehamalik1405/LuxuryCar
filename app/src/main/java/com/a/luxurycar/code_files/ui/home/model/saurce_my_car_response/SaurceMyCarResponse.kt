package com.a.luxurycar.code_files.ui.home.model.saurce_my_car_response


import com.google.gson.annotations.SerializedName

data class SaurceMyCarResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)