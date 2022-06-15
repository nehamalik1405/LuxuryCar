package com.a.luxurycar.code_files.ui.home.model.update_details

import com.google.gson.annotations.SerializedName


data class UpdateBuyerProfileImageModel (

  @SerializedName("status"           ) var status           : Int?              = null,
  @SerializedName("message"          ) var message          : String?           = null,
  @SerializedName("data" ) var buyerProfileData : BuyerProfileData? = BuyerProfileData()

)