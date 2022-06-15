package com.a.luxurycar.code_files.ui.auth.model.register

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("name" ) var name : String? = null
)
