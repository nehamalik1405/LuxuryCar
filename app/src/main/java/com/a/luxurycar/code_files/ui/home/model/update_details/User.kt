package com.a.luxurycar.code_files.ui.home.model.update_details


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("about")
    val about: Any,
    @SerializedName("device_id")
    val deviceId: String,
    @SerializedName("device_token")
    val deviceToken: String,
    @SerializedName("device_type")
    val deviceType: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("status")
    val status: String
)