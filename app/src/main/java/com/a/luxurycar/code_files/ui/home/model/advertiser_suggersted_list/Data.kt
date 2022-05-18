package com.a.luxurycar.code_files.ui.home.model.advertiser_suggersted_list

import com.google.gson.annotations.SerializedName


data class Data (

  @SerializedName("banners"       ) var banners       : Banners?       = Banners(),
  @SerializedName("suggestedCars" ) var suggestedCars : SuggestedCars? = SuggestedCars(),
  @SerializedName("premiumCars"   ) var premiumCars   : PremiumCars?   = PremiumCars(),
  @SerializedName("promotedCars"  ) var promotedCars  : PromotedCars?  = PromotedCars()

)