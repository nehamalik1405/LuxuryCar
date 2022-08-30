package com.a.luxurycar.code_files.ui.seller_deshboard.model.find_garages


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("city_id")
    val cityId: Any,
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("country_code")
    val countryCode: String,
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
    @SerializedName("dial_code")
    val dialCode: Any,
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
    @SerializedName("sale_cars")
    val sale_cars: Int,
    @SerializedName("rent_cars")
    val rent_cars: Int,
    @SerializedName("updated_at")
    val updatedAt: String
)