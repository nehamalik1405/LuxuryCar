package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService

class FindGaragesRepository(val api:ApiService):BaseRepository(){

    suspend fun getFindGaragesResponse() = safeApiCall {
        api.getFindGaragesResponse()
    }


}