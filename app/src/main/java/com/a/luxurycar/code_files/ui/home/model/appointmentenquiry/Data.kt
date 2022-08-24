package com.a.luxurycar.code_files.ui.home.model.appointmentenquiry


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("car_model_id")
    val carModelId: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("make_id")
    val makeId: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("subject")
    val subject: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("year")
    val year: String
)