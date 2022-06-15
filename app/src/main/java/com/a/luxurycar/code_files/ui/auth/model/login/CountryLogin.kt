package com.a.luxurycar.code_files.ui.auth.model.login

import com.google.gson.annotations.SerializedName

data class CountryLogin(
    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("name" ) var name : String? = null
)
