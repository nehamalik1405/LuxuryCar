package com.a.luxurycar.code_files.ui.auth.model.forgot_password

import com.google.gson.annotations.SerializedName

data class OtpModel(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : String? = null
)
