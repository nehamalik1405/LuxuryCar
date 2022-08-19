package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService
import okhttp3.RequestBody

class ContactRepository(val api:ApiService):BaseRepository() {

    suspend fun getContactUSResponse(body: RequestBody) = safeApiCall {
        api.getContactUSResponse(body)
    }
}