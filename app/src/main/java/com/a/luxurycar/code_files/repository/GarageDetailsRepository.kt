package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService

class GarageDetailsRepository(val api:ApiService):BaseRepository() {

    suspend fun getGaragesDetailsResponse(id:String) = safeApiCall {
        api.getGaragesDetailsResponse(id)
    }
}