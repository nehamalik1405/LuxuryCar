package com.a.luxurycar.code_files.ui.home.model

import com.a.luxurycar.code_files.ui.home.model.home_response.Data
import com.google.gson.annotations.SerializedName


data class HomeResponse (

  @SerializedName("status"  ) var status  : Int?    = null,
  @SerializedName("message" ) var message : String? = null,
  @SerializedName("data"    ) var data    : Data?   = Data()

)