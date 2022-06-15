package com.a.luxurycar.common.requestresponse


import com.a.luxurycar.code_files.ui.auth.model.country.CountryListModel
import com.a.luxurycar.code_files.ui.auth.model.forgot_password.OtpModel
import com.a.luxurycar.code_files.ui.auth.model.login.LoginResponse
import com.a.luxurycar.code_files.ui.auth.model.register.RegistrationResponse
import com.a.luxurycar.code_files.ui.auth.model.seller.SellerRegistrationModel
import com.a.luxurycar.code_files.ui.home.model.advertiser_suggersted_list.AdvertiserSuggestedListModel
import com.a.luxurycar.code_files.ui.home.model.change_password.ChangePasswordModel
import com.a.luxurycar.code_files.ui.home.model.update_details.UpdateBuyerProfileImageModel
import com.a.luxurycar.code_files.ui.seller_deshboard.UpdateSellerProfileImageModel
import okhttp3.MultipartBody

import okhttp3.RequestBody
import retrofit2.http.*


interface ApiService {

    // login api
    @POST("login")
    suspend fun getLoginResponse(@Body body: RequestBody): LoginResponse

    // Buyer registration api
    @POST("buyers/registerAccount")
    suspend fun getRegisterResponse(@Body body: RequestBody): RegistrationResponse

    //get country list
    @GET("get-countries-list" )
    suspend fun getCountryList(): CountryListModel

    //get State list
    @GET("states-list-by-country-id/{id}" )
    suspend fun getStateList(@Path("id") state_Id: String): CountryListModel

    //get cities list
    @GET("cities-list-by-state-id/{id}" )
    suspend fun getCitiesList(@Path("id") cityId: String): CountryListModel

    // seller registration api
    @POST("sellers/registerAccount")
    suspend fun getSellerRegisterResponse(@Body body: RequestBody): SellerRegistrationModel

    //update seller details
    @FormUrlEncoded
    @PUT("sellers/update?company_name=Tata Motors 2 mn&email=tusharSharm2@braintechnosys.com&password=admin1234&password_confirmation=admin1234&phone=9878565200&location=https://google.com&description=Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa.")
    suspend fun getUpdateSellerResponse(
        @Field("company_name") company_name : String,
        @Field("email") email : String,
        @Field("password") password : String,
        @Field("password_confirmation") password_confirmation : String,
        @Field("phone") phone : String,
        @Field("location") location : String,
        @Field("description") description : String
    ) : SellerRegistrationModel

    // upload seller image
    @Multipart
    @POST("sellers/updateImage")
    suspend fun updateSellerImage(
        @Part image: MultipartBody.Part
    ): UpdateSellerProfileImageModel

    // change password api
    @POST("users/changePassword")
    suspend fun getChangePasswordResponse(@Body body: RequestBody): ChangePasswordModel

    // home page api
    @POST("?price_from=100000&price_to=1000000")
    suspend fun getAdvertiserSuggestedListResponse(): AdvertiserSuggestedListModel

    // send otp api
    @POST("otps/send")
    suspend fun getSendOtpResponse(@Body body: RequestBody): OtpModel

    // varify otp api
    @POST("otps/verify")
    suspend fun getVarifyOtpResponse(@Body body: RequestBody): OtpModel



    //update buyer details
    @FormUrlEncoded
    @PUT("buyers/update?firstname=Radheshyam&lastname=Saraswat&email=radheshyam8@braintechnosys.biz&phone=9876543251&password=admin123&password_confirmation=admin123&country_id=2&state_id=1&city_id=1")
    suspend fun getUpdateDetails(@Field("firstname") firstname:String,
                                 @Field("lastname") lastname:String,
                                 @Field("email") email:String,
                                 @Field("phone") phone:String,
                                 @Field("password") password:String,
                                 @Field("password_confirmation") password_confirmation:String,
                                 @Field("country_id") country_id:String,
                                 @Field("state_id") state_id:String,
                                 @Field("city_id") city_id:String): LoginResponse

    // upload buyer profile image
    @Multipart
    @POST("buyers/updateImage")
    suspend fun getUploadBuyerProfileImage(
        @Part image: MultipartBody.Part
    ): UpdateBuyerProfileImageModel

    //update buyer password
    @FormUrlEncoded
    @PUT("updatePassword?email=vandana@braintechnosys.biz&newpassword=admin1&newpassword_confirmation=admin1")
    suspend fun getUpdatePasswordResponse(@Field("email") email:String,
                                 @Field("newpassword") newpassword:String,
                                 @Field("newpassword_confirmation") newpassword_confirmation:String): OtpModel
}