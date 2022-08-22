package com.a.luxurycar.code_files.ui.home.model.product_detail_response


import com.google.gson.annotations.SerializedName

data class CarImageX(
    @SerializedName("car_ads_id")
    val carAdsId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("is_featured")
    val isFeatured: String,
    @SerializedName("order")
    val order: Int
)