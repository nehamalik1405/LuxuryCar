package com.a.luxurycar.code_files.ui.add_car.model.add_car_step_two


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("city_id")
    val cityId: Any,
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("country_id")
    val countryId: Any,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("device_id")
    val deviceId: String,
    @SerializedName("device_token")
    val deviceToken: String,
    @SerializedName("device_type")
    val deviceType: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstname")
    val firstname: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("lastname")
    val lastname: Any,
    @SerializedName("location")
    val location: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("state_id")
    val stateId: Any,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String
)