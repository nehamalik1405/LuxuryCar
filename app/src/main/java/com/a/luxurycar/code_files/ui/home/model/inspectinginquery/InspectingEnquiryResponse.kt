package com.a.luxurycar.code_files.ui.home.model.inspectinginquery


import com.google.gson.annotations.SerializedName

data class InspectingEnquiryResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)