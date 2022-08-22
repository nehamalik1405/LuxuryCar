package com.a.luxurycar.code_files.ui.seller_deshboard.model.seller_dashboard


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("country_code")
    val countryCode: Any,
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
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("role")
    val role: String
)