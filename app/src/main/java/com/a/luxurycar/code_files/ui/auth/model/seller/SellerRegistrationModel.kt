package com.a.luxurycar.code_files.ui.auth.model.seller


import com.a.luxurycar.code_files.ui.auth.model.login.Data
import com.google.gson.annotations.SerializedName

data class SellerRegistrationModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Data,
    @SerializedName("status")
    val status: Int
)