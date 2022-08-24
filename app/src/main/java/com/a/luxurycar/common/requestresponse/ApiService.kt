package com.a.luxurycar.common.requestresponse


import com.a.luxurycar.code_files.ui.add_car.model.add_car_step_one.AddCarStepOneModel
import com.a.luxurycar.code_files.ui.add_car.model.add_car_step_three_plan_response.AddCarStepThreeSelectedPlanResponse
import com.a.luxurycar.code_files.ui.add_car.model.add_car_step_two.AddCarStepTwoResponse
import com.a.luxurycar.code_files.ui.add_car.model.body_type.BodyTypeListModel
import com.a.luxurycar.code_files.ui.add_car.model.car_model.CarModelListModel
import com.a.luxurycar.code_files.ui.add_car.model.cities_list.CitiesListModel
import com.a.luxurycar.code_files.ui.add_car.model.make_list_model.MakeListModel
import com.a.luxurycar.code_files.ui.add_car.model.sale_person.SalePersonModelList
import com.a.luxurycar.code_files.ui.add_car.model.sell_car_step_one_basic_list.SellCarStepOneBasicListModel
import com.a.luxurycar.code_files.ui.add_car.model.step_three_listing_plan.AddCarStepThreeListingPlanModel
import com.a.luxurycar.code_files.ui.add_car.model.upload_images.AddCarStepOneUploadImagesModel
import com.a.luxurycar.code_files.ui.auth.model.country.CountryListModel
import com.a.luxurycar.code_files.ui.auth.model.forgot_password.OtpModel
import com.a.luxurycar.code_files.ui.auth.model.login.LoginResponse
import com.a.luxurycar.code_files.ui.auth.model.register.RegistrationResponse
import com.a.luxurycar.code_files.ui.auth.model.seller.SellerRegistrationModel
import com.a.luxurycar.code_files.ui.home.model.HomeResponse
import com.a.luxurycar.code_files.ui.home.model.appointmentenquiry.BookAppointmentEnquiryResponse
import com.a.luxurycar.code_files.ui.home.model.car_list_response.CarListResponse
import com.a.luxurycar.code_files.ui.home.model.change_password.ChangePasswordModel
import com.a.luxurycar.code_files.ui.home.model.contactus.ContactUsResponse
import com.a.luxurycar.code_files.ui.home.model.faqresponse.FAQResponse
import com.a.luxurycar.code_files.ui.home.model.inspectinginquery.InspectingEnquiryResponse
import com.a.luxurycar.code_files.ui.home.model.product_detail_response.ProductDetailsResponse
import com.a.luxurycar.code_files.ui.home.model.search_ads_response.SearchAdsResponse
import com.a.luxurycar.code_files.ui.home.model.update_details.UpdateBuyerProfileImageModel
import com.a.luxurycar.code_files.ui.seller_deshboard.UpdateSellerProfileImageModel
import com.a.luxurycar.code_files.ui.seller_deshboard.model.UpdateSellerProfileModel
import com.a.luxurycar.code_files.ui.seller_deshboard.model.delete_car_response.DeleteCarResponse
import com.a.luxurycar.code_files.ui.seller_deshboard.model.find_garages.FindGaragesResponse
import com.a.luxurycar.code_files.ui.seller_deshboard.model.seller_car_list.SellerCarListResponse
import com.a.luxurycar.code_files.ui.seller_deshboard.model.seller_dashboard.SellerDetailDashboardResponse
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
    @GET("get-countries-list")
    suspend fun getCountryList(): CountryListModel

    //get State list
    @GET("states-list-by-country-id/{id}")
    suspend fun getStateList(@Path("id") state_Id: String): CountryListModel

    //get cities list
    //@GET("cities-list-by-country-id/{id}" )
    @GET("cities-list-by-state-id/{id}")
    suspend fun getCitiesList(@Path("id") cityId: String): CountryListModel

    // seller registration api
    @POST("sellers/registerAccount")
    suspend fun getSellerRegisterResponse(@Body body: RequestBody): SellerRegistrationModel

    //update seller details
    @FormUrlEncoded
    @PUT("sellers/update")
    suspend fun getUpdateSellerResponse(
        @Field("company_name") company_name: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("location") location: String,
        @Field("description") description: String,
    ): UpdateSellerProfileModel

    // upload seller image
    @Multipart
    @POST("sellers/updateImage")
    suspend fun updateSellerImage(
        @Part image: MultipartBody.Part,
    ): UpdateSellerProfileImageModel

    // change password api
    @POST("users/changePassword")
    suspend fun getChangePasswordResponse(@Body body: RequestBody): ChangePasswordModel

    // home page api
    @GET(Const.HOME_PAGE_URL)
    suspend fun getAdvertiserSuggestedListResponse(): HomeResponse

    // home page api
    @POST("search-ads")
    suspend fun getsearchAdsResponseResponse(@Body requestBody: RequestBody): SearchAdsResponse
    // product detail fragments
    @GET("product-detail/{id}")
    suspend fun getProductDetailResponse(@Path("id") Id: String): ProductDetailsResponse

    // send otp api
    @POST("otps/send")
    suspend fun getSendOtpResponse(@Body body: RequestBody): OtpModel

    // varify otp api
    @POST("otps/verify")
    suspend fun getVarifyOtpResponse(@Body body: RequestBody): OtpModel


    //update buyer details
    @FormUrlEncoded
    @PUT("buyers/update")
    suspend fun getUpdateDetails(
        @Field("firstname") firstname: String,
        @Field("lastname") lastname: String,
        @Field("email") email: String,
        @Field("phone") phone: String,

        @Field("country_id") country_id: String,
        @Field("state_id") state_id: String,
        @Field("city_id") city_id: String,
    ): LoginResponse

    // upload buyer profile image
    @Multipart
    @POST("buyers/updateImage")
    suspend fun getUploadBuyerProfileImage(
        @Part image: MultipartBody.Part,
    ): UpdateBuyerProfileImageModel

    //update buyer password
    @FormUrlEncoded
    @PUT("updatePassword")
    suspend fun getUpdatePasswordResponse(
        @Field("email") email: String,
        @Field("newpassword") newpassword: String,
        @Field("newpassword_confirmation") newpassword_confirmation: String,
    ): OtpModel

    // seller car get makes list
    @GET("get-makes-list")
    suspend fun getMakeListResponse(): MakeListModel

    // seller car get makes list
    @GET("cities-list-by-country-id/{id}")
    suspend fun getAddCarStepCitiesResponse(@Path("id") cityId: String): CitiesListModel

    // seller car get body type list
    @GET("body-types-list-by-make-id/{id}")
    suspend fun getBodyTypeResponse(@Path("id") makeId: String): BodyTypeListModel

    // seller car get car model list
    @GET("car-models-list-by-make-id/{id}")
    suspend fun getCarModelResponse(@Path("id") makeId: String): CarModelListModel


    // seller car get car model list
    @GET("sell-car-step-1-basic-list")
    suspend fun getSellCarStepOneResponse(): SellCarStepOneBasicListModel

    // seller car get car model list
    @GET("get-sales-person-list-by-seller-id/")
    suspend fun getSalePersonResponse(@Path("id") salePersonId: String): SalePersonModelList


    // seller car get car model list
    @POST("sell-car/step-1")
    suspend fun getAddCarStepOneResponse(@Body body: RequestBody): AddCarStepOneModel

    // seller car upload multiple images
    @Multipart
    @POST("sell-car/step-1/image-upload")
    suspend fun getMultipleUploadImagesResponse(
        @Part(Const.PARAM_CAR_ADS_ID) id: RequestBody?,
        @Part image: ArrayList<MultipartBody.Part>,
    ): AddCarStepOneUploadImagesModel

    //seller car plan list
    @GET("sell-car/step-2/{id}")
    suspend fun getAddCarStepTwoResponse(@Path("id") id: String): AddCarStepTwoResponse

    //seller car plan list
    @GET("sell-car/step-3")
    suspend fun getAddSellerListingPlan(): AddCarStepThreeListingPlanModel

    //seller car plan list
    @POST("sell-car/step-3")
    suspend fun getAddCarStepThreeSelectedPlan(@Body body: RequestBody): AddCarStepThreeSelectedPlanResponse

    @GET("seller/detail")
    suspend fun getSellerDetailDasboardResponse(): SellerDetailDashboardResponse

    @GET("seller/car-list")
    suspend fun getSellerForSaleListResponse(@Query("rent") rent: String): SellerCarListResponse


    @GET("seller/car-list")
    suspend fun getSellerForRenListResponse(@Query("rent") rent: String): SellerCarListResponse

    @DELETE("delete-car/{id}")
    suspend fun getDeleteCarResponse(@Path("id") id: String): DeleteCarResponse

    @GET("car-listings/{id}")
    suspend fun getCarListResponse(@Path("id") id: String): CarListResponse

    @GET("find-garages")
    suspend fun getFindGaragesResponse(): FindGaragesResponse

    @POST("general-enquiry")
    suspend fun getContactUSResponse(@Body body: RequestBody): ContactUsResponse

    @GET("faqs-listing")
    suspend fun getFAQListResponse(): FAQResponse

    @POST("inspecting-enquiry")
    suspend fun getinspectingEnquiryResponse(@Body body: RequestBody): InspectingEnquiryResponse

   @POST("appointment-enquiry")
    suspend fun getBookAppointmentEnquiryResponse(@Body body: RequestBody): BookAppointmentEnquiryResponse




}