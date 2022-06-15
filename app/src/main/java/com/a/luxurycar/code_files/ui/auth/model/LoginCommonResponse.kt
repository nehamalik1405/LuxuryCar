package com.a.luxurycar.code_files.ui.auth.model

import com.a.luxurycar.code_files.ui.auth.model.login.CityLogin
import com.a.luxurycar.code_files.ui.auth.model.login.CountryLogin
import com.a.luxurycar.code_files.ui.auth.model.login.StateLogin
import com.google.gson.annotations.SerializedName

class LoginCommonResponse(
    val email: String? = null,
    var firstname: String? = null,
    val fullName: String? = null,
    val id: Int = 0,
    var image: String? = null,
    var lastname: String? = null,
    var phone: String? = null,
    val role: String? = null,
    val companyName: String? = null,
    var location: String? = null,
    var description: String? = null,
    var country     : CountryLogin? = CountryLogin(),
    var state       : StateLogin?   = StateLogin(),
    var countryId   : Int?     = null,
    var stateId     : Int?     = null,
    var cityId      : Int?     = null,
    var city        : CityLogin?    = CityLogin()
)