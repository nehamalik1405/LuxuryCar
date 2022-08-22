package com.a.luxurycar.code_files.ui.home.model.car_list_response


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("company_name")
    val companyName: String,
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
    @SerializedName("phone")
    val phone: String
)