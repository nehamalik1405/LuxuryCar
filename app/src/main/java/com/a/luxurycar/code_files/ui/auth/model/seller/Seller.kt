package com.a.luxurycar.code_files.ui.auth.model.seller


import com.google.gson.annotations.SerializedName

data class Seller(
    @SerializedName("company_name")
    val companyName: String,
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