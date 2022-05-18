package com.a.luxurycar.code_files.ui.home.model.change_password


import com.google.gson.annotations.SerializedName

data class ChangePasswordModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)