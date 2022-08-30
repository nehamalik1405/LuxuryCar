package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService

class InspectingRepository(val api:ApiService):BaseRepository() {
    suspend fun getCmsResponse() = safeApiCall {
        api.getCmsResponse()
    }
}