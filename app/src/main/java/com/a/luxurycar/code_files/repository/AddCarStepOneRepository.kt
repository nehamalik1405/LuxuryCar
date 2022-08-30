package com.a.luxurycar.code_files.repository

import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddCarStepOneRepository(val api:ApiService):BaseRepository(){

    suspend fun getMakeListResponse()= safeApiCall {
        api.getMakeListResponse()
    }

    suspend fun getBodyTypeResponse(makeId: String) = safeApiCall {
        api.getBodyTypeResponse(makeId)
    }
    suspend fun getCarModelResponse(makeId: String) = safeApiCall {
        api.getCarModelResponse(makeId)
    }
    suspend fun getSellCarStepOneResponse() = safeApiCall {
        api.getSellCarStepOneResponse()
    }

    suspend fun getAddCarStepCitiesResponse(cityId: String) = safeApiCall {
         api.getAddCarStepCitiesResponse(cityId)
    }
    suspend fun getSalePersonResponse(salePersonId: String) = safeApiCall {
        api.getSalePersonResponse(/*salePersonId*/)
    }

    suspend fun getAddCarStepOneResponse(body: RequestBody) = safeApiCall {
        api.getAddCarStepOneResponse(body)
    }

    suspend fun getMultipleUploadImagesResponse(id:RequestBody, image: ArrayList<MultipartBody.Part>) = safeApiCall {
        api.getMultipleUploadImagesResponse(id,image)
    }

}