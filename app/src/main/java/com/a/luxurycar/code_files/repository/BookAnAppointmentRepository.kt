package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.common.requestresponse.ApiService
import okhttp3.RequestBody

class BookAnAppointmentRepository(val api:ApiService):BaseRepository() {

    suspend fun getMakeListResponse()= safeApiCall {
        api.getMakeListResponse()
    }
    suspend fun getCarModelResponse(makeId: String) = safeApiCall {
        api.getCarModelResponse(makeId)
    }

    suspend fun getBookAppointmentEnquiryResponse(body: RequestBody) = safeApiCall {
        api.getBookAppointmentEnquiryResponse(body)
    }
}