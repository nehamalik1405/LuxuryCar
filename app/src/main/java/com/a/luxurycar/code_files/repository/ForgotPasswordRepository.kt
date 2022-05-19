package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService
import okhttp3.RequestBody

class ForgotPasswordRepository(val api:ApiService): BaseRepository() {

    suspend fun getSendOtpResponse(body: RequestBody) = safeApiCall {
        api.getSendOtpResponse(body)
    }


    suspend fun getVerifyResponse(body: RequestBody) = safeApiCall {
        api.getVarifyOtpResponse(body)
    }
    suspend fun getUpdatePasswordResponse(email:String,new_password:String,confirm_password:String) = safeApiCall {
        api.getUpdatePasswordResponse(email,new_password,confirm_password)
    }
}