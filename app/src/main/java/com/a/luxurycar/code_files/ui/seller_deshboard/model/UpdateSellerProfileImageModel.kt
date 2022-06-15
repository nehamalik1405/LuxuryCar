package com.a.luxurycar.code_files.ui.seller_deshboard

import com.a.luxurycar.code_files.ui.seller_deshboard.model.Data
import com.google.gson.annotations.SerializedName


data class UpdateSellerProfileImageModel (

  @SerializedName("status"  ) var status  : Int?    = null,
  @SerializedName("message" ) var message : String? = null,
  @SerializedName("data"    ) var data    : Data?   = Data()
)