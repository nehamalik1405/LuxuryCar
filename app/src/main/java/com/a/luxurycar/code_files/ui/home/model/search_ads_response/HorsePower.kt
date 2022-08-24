package com.a.luxurycar.code_files.ui.home.model.search_ads_response


import com.google.gson.annotations.SerializedName

data class HorsePower(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)