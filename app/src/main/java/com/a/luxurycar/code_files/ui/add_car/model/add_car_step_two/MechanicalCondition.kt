package com.a.luxurycar.code_files.ui.add_car.model.add_car_step_two


import com.google.gson.annotations.SerializedName

data class MechanicalCondition(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String
)