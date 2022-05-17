package com.a.luxurycar.common.requestresponse


import com.a.luxurycar.code_files.ui.auth.model.login.LoginResponse
import com.a.luxurycar.code_files.ui.auth.model.register.RegistrationResponse
import com.a.luxurycar.code_files.ui.home.model.UpdateDetailModel

import okhttp3.RequestBody
import retrofit2.http.*


interface ApiService {

    // login api
    @POST("login")
    suspend fun getLoginResponse(@Body body: RequestBody): LoginResponse

    // registration api
    @POST("buyers/registerAccount")
    suspend fun getRegisterResponse(@Body body: RequestBody): RegistrationResponse

    //update buyer details
    @FormUrlEncoded
    @PUT("buyers/update?firstname=Radheshyam&lastname=Saraswat&email=shiwali@braintechnosys.biz&phone=9876543250")
    suspend fun getUpdateDetails(@Field("firstname") firstname:String,
                                        @Field("lastname") lastname:String,
                                        @Field("email") email:String,
                                        @Field("phone") phone:String): UpdateDetailModel
}