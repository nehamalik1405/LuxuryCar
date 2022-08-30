package com.a.luxurycar.code_files.ui.home.model.search_ads_response


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("Search data")
    val searchData: List<SearchData?>?
)