package com.a.luxurycar.code_files.ui.home.model.advertiser_suggersted_list

import com.google.gson.annotations.SerializedName


data class Make (

  @SerializedName("id"   ) var id   : Int?    = null,
  @SerializedName("name" ) var name : String? = null

)