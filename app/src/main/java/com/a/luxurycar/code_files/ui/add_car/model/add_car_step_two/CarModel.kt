package com.a.luxurycar.code_files.ui.add_car.model.add_car_step_two


import com.google.gson.annotations.SerializedName

data class CarModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)