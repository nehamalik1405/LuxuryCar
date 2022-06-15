package com.a.luxurycar.common.helper;

import android.content.Context
import android.content.SharedPreferences
import com.a.luxurycar.code_files.ui.auth.model.LoginCommonResponse
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
        val KEY_USER_ROLE = "user_role" //
        val KEY_AUTHORIZATION_TOKEN = "authorization_token" //
        val KEY_USER_DATA = "user_data" //
        val IS_USER_LOGGED_IN = "is_user_logged_in" //
        val KEY_CURRENT_ITEM_VIEWPAGER = "viewpager_current_item"

        fun saveUserData(loginData: LoginCommonResponse) {
            PrefUtil.putBoolean(LuxuryCarApplication.instance, IS_USER_LOGGED_IN, true)
            PrefUtil.putString(LuxuryCarApplication.instance, KEY_USER_DATA, Gson().toJson(loginData))
        }

        public fun getUserData(): LoginCommonResponse? {
            return Gson().fromJson(PrefUtil.getString(LuxuryCarApplication.instance, KEY_USER_DATA,""), LoginCommonResponse::class.java)
        }

        fun setUserLoggedIn(isUserLoggedIn : Boolean) {
            PrefUtil.putBoolean(LuxuryCarApplication.instance, IS_USER_LOGGED_IN, isUserLoggedIn)
        }

        fun isUserLoggedIn(): Boolean {
            return PrefUtil.getBoolean(LuxuryCarApplication.instance, IS_USER_LOGGED_IN, false)
        }

        fun setAuthorizationToken(authToken : String) {
            PrefUtil.putString(LuxuryCarApplication.instance, KEY_AUTHORIZATION_TOKEN, authToken)
        }

        fun getAuthorizationToken(): String {
            return PrefUtil.getString(LuxuryCarApplication.instance, KEY_AUTHORIZATION_TOKEN, "")?:""
        }

        fun setUserRole(userRole : String) {
            PrefUtil.putString(LuxuryCarApplication.instance, KEY_USER_ROLE, userRole)
        }

        fun getUserRole(): String {
            return PrefUtil.getString(LuxuryCarApplication.instance, KEY_USER_ROLE, "")?:""
        }










        fun setViewPagerItem(item : String) {
            PrefUtil.putString(LuxuryCarApplication.instance, KEY_CURRENT_ITEM_VIEWPAGER, item)
        }


        fun getViewPagerItem(): String?{
            return PrefUtil.getString(LuxuryCarApplication.instance, KEY_CURRENT_ITEM_VIEWPAGER,"")

        }








    }

    fun logout() {
        PrefUtil.clear(context)
    }
}

