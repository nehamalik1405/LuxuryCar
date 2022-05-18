package com.a.luxurycar.code_files.ui.home.model.advertiser_suggersted_list

import com.google.gson.annotations.SerializedName


data class AdvertiserSuggestedListModel (

  @SerializedName("status"  ) var status  : Int?    = null,
  @SerializedName("message" ) var message : String? = null,
  @SerializedName("data"    ) var data    : Data?   = Data()

)