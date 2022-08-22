package com.a.luxurycar.code_files.ui.home.model.home_response

import com.google.gson.annotations.SerializedName

data class ListingPlan(

    @SerializedName("id"    ) var id    : Int?    = null,
    @SerializedName("title" ) var title : String? = null

)
