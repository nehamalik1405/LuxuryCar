package com.a.luxurycar.code_files.ui.add_car.model.car_model


import com.google.gson.annotations.SerializedName

data class CarModelListModel(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)