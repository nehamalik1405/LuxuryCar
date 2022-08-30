package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService

class CarListRepository(val api:ApiService):BaseRepository() {

    suspend fun getCarListResponse(/*id: String,*/ sortByID: String) = safeApiCall {

        api.getCarListResponse(/*id,*/sortByID)
    }
}