package com.a.luxurycar.code_files.ui.auth.model.seller


import com.google.gson.annotations.SerializedName

data class SellerRegistrationModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("SellerData")
    val sellerData: SellerData,
    @SerializedName("status")
    val status: Int
)