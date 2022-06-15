package com.a.luxurycar.code_files.ui.home.model.update_details

import com.google.gson.annotations.SerializedName


data class BuyerProfileData (

  @SerializedName("id"           ) var id           : Int?          = null,
  @SerializedName("role"         ) var role         : String?       = null,
  @SerializedName("firstname"    ) var firstname    : String?       = null,
  @SerializedName("lastname"     ) var lastname     : String?       = null,
  @SerializedName("email"        ) var email        : String?       = null,
  @SerializedName("phone"        ) var phone        : String?       = null,
  @SerializedName("country_id"   ) var countryId    : Int?          = null,
  @SerializedName("state_id"     ) var stateId      : Int?          = null,
  @SerializedName("city_id"      ) var cityId       : Int?          = null,
  @SerializedName("image"        ) var image        : String?       = null,
  @SerializedName("device_id"    ) var deviceId     : String?       = null,
  @SerializedName("device_token" ) var deviceToken  : String?       = null,
  @SerializedName("device_type"  ) var deviceType   : String?       = null,
  @SerializedName("fullname"     ) var fullname     : String?       = null,
  @SerializedName("BuyerCountry" ) var BuyerCountry : BuyerCountry? = BuyerCountry(),
  @SerializedName("BuyerState"   ) var BuyerState   : BuyerState?   = BuyerState(),
  @SerializedName("BuyerCity"    ) var BuyerCity    : BuyerCity?    = BuyerCity()

)