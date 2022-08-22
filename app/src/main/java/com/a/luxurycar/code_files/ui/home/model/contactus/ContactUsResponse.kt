package com.a.luxurycar.code_files.ui.home.model.contactus


import com.google.gson.annotations.SerializedName

data class ContactUsResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)