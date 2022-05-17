package com.a.luxurycar.code_files.ui.home.model.update_details


import com.google.gson.annotations.SerializedName

data class UpdateDetailsModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)