package com.a.luxurycar.code_files.ui.add_car.model.add_car_step_one


import com.google.gson.annotations.SerializedName

data class AddCarStepOneModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)