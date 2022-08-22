package com.a.luxurycar.code_files.ui.seller_deshboard.model.seller_car_list


import com.google.gson.annotations.SerializedName

data class SellerCarListResponse(
    @SerializedName("data")
    val `data`: ArrayList<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)