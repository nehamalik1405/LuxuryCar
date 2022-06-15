package com.a.luxurycar.code_files.ui.seller_deshboard.model

import com.google.gson.annotations.SerializedName

data class Data(

    @SerializedName("id"           ) var id          : Int?    = null,
    @SerializedName("role"         ) var role        : String? = null,
    @SerializedName("company_name" ) var companyName : String? = null,
    @SerializedName("email"        ) var email       : String? = null,
    @SerializedName("phone"        ) var phone       : String? = null,
    @SerializedName("location"     ) var location    : String? = null,
    @SerializedName("description"  ) var description : String? = null,
    @SerializedName("image"        ) var image       : String? = null,
    @SerializedName("device_id"    ) var deviceId    : String? = null,
    @SerializedName("device_token" ) var deviceToken : String? = null,
    @SerializedName("device_type"  ) var deviceType  : String? = null


)
