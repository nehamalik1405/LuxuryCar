package com.a.luxurycar.code_files.ui.home.model.advertiser_suggersted_list

import com.google.gson.annotations.SerializedName


data class PromotedCars (

  @SerializedName("list"    ) var list    : ArrayList<Listt> = arrayListOf(),
  @SerializedName("status"  ) var status  : Int?            = null,
  @SerializedName("message" ) var message : String?         = null

)