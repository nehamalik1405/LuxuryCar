package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService
import okhttp3.RequestBody

class LoginRepository(val api: ApiService):BaseRepository() {


    suspend fun getLoginResponse(body: RequestBody) = safeApiCall {
        api.getLoginResponse(body)
    }
}