package com.a.luxurycar.code_files.ui.add_car.model.sale_person


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("country_code")
    val countryCode: Any,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("dial_code")
    val dialCode: Any,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("garage_id")
    val garageId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String
)