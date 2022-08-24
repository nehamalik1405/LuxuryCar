package com.a.luxurycar.code_files.ui.home.model.home_response

import com.google.gson.annotations.SerializedName

data class FeaturedCars(
    @SerializedName("list"    ) var list    : ArrayList<Listt> = arrayListOf(),
    @SerializedName("status"  ) var status  : Int?            = null,
    @SerializedName("message" ) var message : String?         = null
)