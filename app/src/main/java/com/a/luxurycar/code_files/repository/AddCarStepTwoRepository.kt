package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService

class AddCarStepTwoRepository(val api:ApiService):BaseRepository() {
    suspend fun getAddCarStepTwoResponse(id:String) = safeApiCall {
        api.getAddCarStepTwoResponse(id)
    }
}