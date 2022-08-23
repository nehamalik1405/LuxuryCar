package com.a.luxurycar.code_files.ui.home.model.appointmentenquiry


import com.google.gson.annotations.SerializedName

data class BookAppointmentEnquiryResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)