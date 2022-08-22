package com.a.luxurycar.code_files.ui.home.model.faqresponse


import com.google.gson.annotations.SerializedName

data class FAQResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)