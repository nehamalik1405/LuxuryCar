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
}