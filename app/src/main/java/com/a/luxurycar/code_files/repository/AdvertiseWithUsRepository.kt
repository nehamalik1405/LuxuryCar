package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService
import okhttp3.RequestBody

class AdvertiseWithUsRepository(val api:ApiService):BaseRepository(){

    suspend fun getAdvertiserEnquiryResponse(body: RequestBody) = safeApiCall {
        api.getAdvertiserEnquiryResponse(body)
    }

}