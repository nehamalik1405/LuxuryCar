package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService
import okhttp3.RequestBody

class TransportExportRepository(val api:ApiService) :BaseRepository(){

    suspend fun getfaqListResponse() = safeApiCall {
        api.getFAQListResponse()
    }
    suspend fun getCmsResponse() = safeApiCall {
        api.getCmsResponse()
    }


}