package com.a.luxurycar.code_files.ui.auth.model.country


import com.google.gson.annotations.SerializedName

data class CountryListModel(
    @SerializedName("data")
    val `data`: ArrayList<Data>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)