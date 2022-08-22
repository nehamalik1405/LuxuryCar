package com.a.luxurycar.code_files.ui.add_car.model.add_car_step_two


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("car_ads")
    val carAds: ArrayList<CarAd>,
    @SerializedName("car_images")
    val carImages: ArrayList<CarImage>,
    @SerializedName("details")
    val details: ArrayList<Detail>
)