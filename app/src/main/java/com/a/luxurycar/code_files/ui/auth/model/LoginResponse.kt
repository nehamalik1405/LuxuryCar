package com.a.luxurycar.code_files.ui.auth.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : String? = null

)



