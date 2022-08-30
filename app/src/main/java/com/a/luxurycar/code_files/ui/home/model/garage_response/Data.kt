package com.a.luxurycar.code_files.ui.home.model.garage_response


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("garage_car_list")
    val garageCarList: List<GarageCar?>?,
    @SerializedName("garage_detail")
    val garageDetail: GarageDetail?
)