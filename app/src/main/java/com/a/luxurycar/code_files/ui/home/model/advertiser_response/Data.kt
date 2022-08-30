package com.a.luxurycar.code_files.ui.home.model.advertiser_response


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("company_name")
    val companyName: Any?,
    @SerializedName("country_code")
    val countryCode: Any?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("dial_code")
    val dialCode: Any?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("firstname")
    val firstname: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("lastname")
    val lastname: String?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("subject")
    val subject: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)