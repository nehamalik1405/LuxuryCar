package com.a.luxurycar.code_files.ui.add_car.model.sell_car_step_one_basic_list


import com.google.gson.annotations.SerializedName

data class RegionalSpecification(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)