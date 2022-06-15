package com.a.luxurycar.code_files.ui.auth.model.login

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
    var firstname: String,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("lastname")
    var lastname: String,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("company_name")
    val company_name: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("user_address")
    val userAddress: Any,
    @SerializedName("country"      )
    var country     : CountryLogin? = CountryLogin(),
    @SerializedName("state"        )
    var state       : StateLogin?   = StateLogin(),
    @SerializedName("country_id"   )
    var countryId   : Int?     = null,
    @SerializedName("state_id"     )
    var stateId     : Int?     = null,
    @SerializedName("city_id"      )
    var cityId      : Int?     = null,
    @SerializedName("city"         )
    var city        : CityLogin?    = CityLogin()

)