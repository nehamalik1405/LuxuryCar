package com.a.luxurycar.code_files.ui.home.model.home_response

import com.google.gson.annotations.SerializedName

data class BannerList(

    @SerializedName("id"      ) var id      : Int?    = null,
    @SerializedName("caption" ) var caption : String? = null,
    @SerializedName("image"   ) var image   : String? = null

)
