package com.a.luxurycar.code_files.ui.home.model

import com.google.gson.annotations.SerializedName


data class CarModel (

  @SerializedName("id"   ) var id   : Int?    = null,
  @SerializedName("name" ) var name : String? = null

)