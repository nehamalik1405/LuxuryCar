package com.a.luxurycar.code_files.ui.add_car.model.add_car_step_two


import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("country_id")
    val countryId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("state_id")
    val stateId: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String
)