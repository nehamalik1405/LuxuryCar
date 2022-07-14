package com.a.luxurycar.code_files.ui.add_car.model.add_car_step_two


import com.google.gson.annotations.SerializedName

data class AddCarStepTwoResponse(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)