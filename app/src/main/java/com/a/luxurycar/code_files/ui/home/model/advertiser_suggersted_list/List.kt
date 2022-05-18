package com.a.luxurycar.code_files.ui.home.model.advertiser_suggersted_list

import com.google.gson.annotations.SerializedName


data class Listt (

  @SerializedName("id"                ) var id               : Int?      = null,
  @SerializedName("name"              ) var name             : String?   = null,
  @SerializedName("make_id"           ) var makeId           : Int?      = null,
  @SerializedName("type_id"           ) var typeId           : Int?      = null,
  @SerializedName("car_model_id"      ) var carModelId       : Int?      = null,
  @SerializedName("car_year"          ) var carYear          : String?   = null,
  @SerializedName("car_company_id"    ) var carCompanyId     : Int?      = null,
  @SerializedName("price"             ) var price            : String?   = null,
  @SerializedName("run_kms"           ) var runKms           : String?   = null,
  @SerializedName("feature"           ) var feature          : String?   = null,
  @SerializedName("is_premium"        ) var isPremium        : String?   = null,
  @SerializedName("transmission_type" ) var transmissionType : String?   = null,
  @SerializedName("rent"              ) var rent             : String?   = null,
  @SerializedName("daily_rent_price"  ) var dailyRentPrice   : String?   = null,
  @SerializedName("weekly_rent_price" ) var weeklyRentPrice  : String?   = null,
  @SerializedName("image" ) var image  : String?   = null,
  @SerializedName("make"              ) var make             : Make?     = Make(),
  @SerializedName("type"              ) var type             : Type?     = Type(),
  @SerializedName("car_model"         ) var carModel         : CarModel? = CarModel()

)