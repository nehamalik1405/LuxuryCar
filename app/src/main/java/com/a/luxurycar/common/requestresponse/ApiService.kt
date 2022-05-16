package com.a.luxurycar.common.requestresponse

import com.a.luxurycar.code_files.ui.auth.model.LoginResponse
import com.a.luxurycar.code_files.ui.auth.model.RegistrationResponse

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

    // login api
    @POST("login")
    suspend fun getLoginResponse(@Body body: RequestBody): LoginResponse

    // login api
    @POST("buyers/registerAccount")
    suspend fun getRegisterResponse(@Body body: RequestBody): RegistrationResponse


}