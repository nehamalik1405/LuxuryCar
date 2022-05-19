package com.a.luxurycar.common.helper;

import android.content.Context
import android.content.SharedPreferences
import com.a.luxurycar.code_files.ui.auth.model.login.LoginResponse
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
        val KEY_AUTHORIZATION_TOKEN = "authorization_token" //
        val KEY_USER_DATA = "user_data" //
        val IS_USER_LOGGED_IN = "is_user_logged_in" //

        fun saveUserData(loginData: LoginResponse) {
            PrefUtil.putBoolean(LuxuryCarApplication.instance, IS_USER_LOGGED_IN, true)
            PrefUtil.putString(LuxuryCarApplication.instance, KEY_USER_DATA, Gson().toJson(loginData))
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
        public fun getUserData(): LoginResponse? {
            return Gson().fromJson(PrefUtil.getString(LuxuryCarApplication.instance, KEY_USER_DATA,""), LoginResponse::class.java)
        }

    }

    fun logout() {
        PrefUtil.clear(context)
    }
}

