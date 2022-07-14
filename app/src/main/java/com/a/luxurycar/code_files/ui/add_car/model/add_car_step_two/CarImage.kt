package com.a.luxurycar.code_files.ui.add_car.model.add_car_step_two


import com.google.gson.annotations.SerializedName

data class CarImage(
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