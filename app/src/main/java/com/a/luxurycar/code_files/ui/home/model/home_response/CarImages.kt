package com.a.luxurycar.code_files.ui.home.model

import com.google.gson.annotations.SerializedName


data class CarImages (

  @SerializedName("id"          ) var id         : Int?    = null,
  @SerializedName("car_ads_id"  ) var carAdsId   : Int?    = null,
  @SerializedName("image"       ) var image      : String? = null,
  @SerializedName("order"       ) var order      : Int?    = null,
  @SerializedName("is_featured" ) var isFeatured : String? = null

)