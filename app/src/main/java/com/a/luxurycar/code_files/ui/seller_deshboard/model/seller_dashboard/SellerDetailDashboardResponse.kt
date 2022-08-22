package com.a.luxurycar.code_files.ui.seller_deshboard.model.seller_dashboard


import com.google.gson.annotations.SerializedName

data class SellerDetailDashboardResponse(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)