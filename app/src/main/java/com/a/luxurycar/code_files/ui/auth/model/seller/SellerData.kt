package com.a.luxurycar.code_files.ui.auth.model.seller


import com.google.gson.annotations.SerializedName

data class SellerData(
    @SerializedName("Seller")
    val seller: Seller
)