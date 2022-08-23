package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService
import okhttp3.RequestBody

class NewEnquiryFormRepository(val api:ApiService):BaseRepository() {

    suspend fun getinspectingEnquiryResponse(body: RequestBody) = safeApiCall {
        api.getinspectingEnquiryResponse(body)
    }
}