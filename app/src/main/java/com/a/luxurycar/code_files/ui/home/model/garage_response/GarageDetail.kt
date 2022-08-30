package com.a.luxurycar.code_files.ui.home.model.garage_response


import com.google.gson.annotations.SerializedName

data class GarageDetail(
    @SerializedName("company_name")
    val companyName: String?,
    @SerializedName("country_code")
    val countryCode: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("dial_code")
    val dialCode: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("rent_cars")
    val rentCars: Int?,
    @SerializedName("sale_cars")
    val saleCars: Int?
)