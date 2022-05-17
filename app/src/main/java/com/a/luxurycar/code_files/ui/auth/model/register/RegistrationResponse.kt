package com.a.luxurycar.code_files.ui.auth.model.register


import com.google.gson.annotations.SerializedName

data class RegistrationResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)