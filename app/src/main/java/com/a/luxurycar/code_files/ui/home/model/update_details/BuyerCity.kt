package com.a.luxurycar.code_files.ui.home.model.update_details

import com.google.gson.annotations.SerializedName


data class BuyerCity (

  @SerializedName("id"   ) var id   : Int?    = null,
  @SerializedName("name" ) var name : String? = null

)