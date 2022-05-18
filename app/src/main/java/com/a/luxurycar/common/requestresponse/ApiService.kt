package com.a.luxurycar.common.requestresponse



import com.a.luxurycar.code_files.ui.auth.model.forgot_password.SendOtpModel
import com.a.luxurycar.code_files.ui.auth.model.forgot_password.VerifyOtpModel
import com.a.luxurycar.code_files.ui.auth.model.login.LoginResponse
import com.a.luxurycar.code_files.ui.auth.model.register.RegistrationResponse
import com.a.luxurycar.code_files.ui.home.model.advertiser_suggersted_list.AdvertiserSuggestedListModel
import com.a.luxurycar.code_files.ui.home.model.change_password.ChangePasswordModel
import com.a.luxurycar.code_files.ui.home.model.update_details.UpdateDetailsModel

import okhttp3.RequestBody
import retrofit2.http.*


interface ApiService {

    // login api
    @POST("login")
    suspend fun getLoginResponse(@Body body: RequestBody): LoginResponse

    // registration api
    @POST("buyers/registerAccount")
    suspend fun getRegisterResponse(@Body body: RequestBody): RegistrationResponse

    // change password api
    @POST("users/changePassword")
    suspend fun getChangePasswordResponse(@Body body: RequestBody): ChangePasswordModel

    // home page api
    @POST("?price_from=100000&price_to=1000000")
    suspend fun getAdvertiserSuggestedListResponse(): AdvertiserSuggestedListModel

    // send otp api
    @POST("/otps/send")
    suspend fun getSendOtpResponse(@Body body: RequestBody): SendOtpModel

    // varify otp api
    @POST("/otps/verify")
    suspend fun getVarifyOtpResponse(@Body body: RequestBody): VerifyOtpModel



    //update buyer details
    @FormUrlEncoded
    @PUT("buyers/update?firstname=Radheshyam&lastname=Saraswat&email=shiwali@braintechnosys.biz&phone=9876543250")
    suspend fun getUpdateDetails(@Field("firstname") firstname:String,
                                        @Field("lastname") lastname:String,
                                        @Field("email") email:String,
                                        @Field("phone") phone:String): UpdateDetailsModel
}