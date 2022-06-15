package com.a.luxurycar.common.helper;

import android.content.Context
import android.content.SharedPreferences
import com.a.luxurycar.code_files.ui.auth.model.login.LoginResponse
import com.a.luxurycar.code_files.ui.auth.model.seller.SellerRegistrationModel
import com.a.luxurycar.code_files.ui.home.model.update_details.UpdateDetailsModel

import com.a.luxurycar.common.application.LuxuryCarApplication
import com.a.luxurycar.common.utils.PrefUtil
import com.google.gson.Gson


class SessionManager(var context: Context) {

    internal var pref: SharedPreferences;
    internal var editor: SharedPreferences.Editor;

    init {
        pref = context.getSharedPreferences(KEY_USER_PREF, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    companion object {

        val KEY_USER_PREF = "user_pref" //
        val KEY_USER_Email = "user_email" //
        val KEY_USER_Name = "user_name" //
        val KEY_USER_Phone = "user_phone" //
        val KEY_USER_Last_Name = "user_last_name" //
        val KEY_USER_Full_Name = "user_full_name" //
        val KEY_COMPANY_Name = "user_company_name" //
        val KEY_AUTHORIZATION_TOKEN = "authorization_token" //
        val KEY_USER_DATA = "user_data" //
        val KEY_SELLER_DATA = "seller_data" //
        val IS_USER_LOGGED_IN = "is_user_logged_in" //
        val IS_SELLER_LOGGED_IN = "is_seller_logged_in" //
        val KEY_USER_IMAGE_URL = "user_image_url" //
        val KEY_USER_Location = "user_location" //
        val KEY_USER_Descriptio = "user_description" //
        val KEY_CURRENT_ITEM_VIEWPAGER = "viewpager_current_item"

        fun saveUserData(loginData: LoginResponse) {
            PrefUtil.putBoolean(LuxuryCarApplication.instance, IS_USER_LOGGED_IN, true)
            PrefUtil.putString(LuxuryCarApplication.instance, KEY_USER_DATA, Gson().toJson(loginData))
        }
        fun saveSellerData(sellerData: SellerRegistrationModel) {
            PrefUtil.putBoolean(LuxuryCarApplication.instance, IS_USER_LOGGED_IN, true)
            PrefUtil.putString(LuxuryCarApplication.instance, KEY_SELLER_DATA, Gson().toJson(sellerData))
        }

        fun setAuthorizationToken(authToken : String) {
            PrefUtil.putString(LuxuryCarApplication.instance, KEY_AUTHORIZATION_TOKEN, authToken)
        }

       fun setEmail(email : String) {
           PrefUtil.putString(LuxuryCarApplication.instance, KEY_USER_Email, email)
       }
        fun getEmail(): String?{
            return PrefUtil.getString(LuxuryCarApplication.instance, KEY_USER_Email,"")

        }

        fun setFirstName(name : String) {
            PrefUtil.putString(LuxuryCarApplication.instance, KEY_USER_Name, name)
        }
        fun getFirstName(): String?{
            return PrefUtil.getString(LuxuryCarApplication.instance, KEY_USER_Name,"")

        }
        fun setLastName(last_name : String) {
            PrefUtil.putString(LuxuryCarApplication.instance, KEY_USER_Last_Name, last_name)
        }
        fun getLastName(): String?{
            return PrefUtil.getString(LuxuryCarApplication.instance, KEY_USER_Last_Name,"")

        }
        fun setFullName(fullName : String) {
            PrefUtil.putString(LuxuryCarApplication.instance, KEY_USER_Full_Name, fullName)
        }
        fun getFullName(): String?{
            return PrefUtil.getString(LuxuryCarApplication.instance, KEY_USER_Full_Name,"")

        }

        fun setCompanyName(company_name : String) {
            PrefUtil.putString(LuxuryCarApplication.instance, KEY_COMPANY_Name, company_name)
        }
        fun getCompanyName(): String?{
            return PrefUtil.getString(LuxuryCarApplication.instance, KEY_COMPANY_Name,"")

        }
        fun setPhone(phone : String) {
            PrefUtil.putString(LuxuryCarApplication.instance, KEY_USER_Phone, phone)
        }
        fun getPhone(): String?{
            return PrefUtil.getString(LuxuryCarApplication.instance, KEY_USER_Phone,"")
        }
        fun setViewPagerItem(item : String) {
            PrefUtil.putString(LuxuryCarApplication.instance, KEY_CURRENT_ITEM_VIEWPAGER, item)
        }
        fun getViewPagerItem(): String?{
            return PrefUtil.getString(LuxuryCarApplication.instance, KEY_CURRENT_ITEM_VIEWPAGER,"")

        }
        fun setImageUrl(url : String) {
            PrefUtil.putString(LuxuryCarApplication.instance, KEY_USER_IMAGE_URL, url)
        }
        fun getImageUrl(): String?{
            return PrefUtil.getString(LuxuryCarApplication.instance, KEY_USER_IMAGE_URL,"")

        }
        fun setLocation(url : String) {
            PrefUtil.putString(LuxuryCarApplication.instance, KEY_USER_Location, url)
        }
        fun getLocation(): String?{
            return PrefUtil.getString(LuxuryCarApplication.instance, KEY_USER_Location,"")

        }
        fun setDescription(url : String) {
            PrefUtil.putString(LuxuryCarApplication.instance, KEY_USER_Descriptio, url)
        }
        fun getDescription(): String?{
            return PrefUtil.getString(LuxuryCarApplication.instance, KEY_USER_Descriptio,"")

        }

        fun getAuthorizationToken(): String {
            return getUserData()?.data?.let {
                PrefUtil.getString(LuxuryCarApplication.instance, KEY_AUTHORIZATION_TOKEN,
                    it.accessToken)
            } ?:""
        }


        fun setUserLoggedIn(isUserLoggedIn : Boolean) {
            PrefUtil.putBoolean(LuxuryCarApplication.instance, IS_USER_LOGGED_IN, isUserLoggedIn)
        }

        fun isUserLoggedIn(): Boolean {
            return PrefUtil.getBoolean(LuxuryCarApplication.instance, IS_USER_LOGGED_IN, false)
        }
        fun isUserSellerLoggedIn(): Boolean {
            return PrefUtil.getBoolean(LuxuryCarApplication.instance, IS_SELLER_LOGGED_IN, false)
        }
        public fun getUserData(): LoginResponse? {
            return Gson().fromJson(PrefUtil.getString(LuxuryCarApplication.instance, KEY_USER_DATA,""), LoginResponse::class.java)
        }

        fun getSellerData(): LoginResponse? {
            return Gson().fromJson(PrefUtil.getString(LuxuryCarApplication.instance, KEY_USER_DATA,""), LoginResponse::class.java)
        }

    }

    fun logout() {
        PrefUtil.clear(context)
    }
}

