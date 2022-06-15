package com.a.luxurycar.code_files.ui.auth.model.register


import com.a.luxurycar.code_files.ui.auth.model.login.CityLogin
import com.a.luxurycar.code_files.ui.auth.model.login.CountryLogin
import com.a.luxurycar.code_files.ui.auth.model.login.StateLogin
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("about")
    val about: Any,
    @SerializedName("device_id")
    val deviceId: Any,
    @SerializedName("device_token")
    val deviceToken: Any,
    @SerializedName("device_type")
    val deviceType: Any,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstname")
    val firstname: String,
    @SerializedName("fullname")
    val fullname: String,
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
    val status: String,
    @SerializedName("user_address")
    val userAddress: Any,
    @SerializedName("country"      ) var country     : CountryLogin? = CountryLogin(),
    @SerializedName("state"        ) var state       : StateLogin?   = StateLogin(),
    @SerializedName("city"         ) var city        : CityLogin?    = CityLogin()

)