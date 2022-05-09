package com.a.luxurycar.common.requestresponse

import android.se.omapi.Session
import com.a.luxurycar.common.helper.SessionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiAdapter {

    companion object{

      //  var alternateUrl="https://helper.testnet.near.org/"

        var tokenData = ""

        class NoInternetException(override var message: String) : Exception(message)



        fun <Api> buildApi(
            api : Class<Api>,
            baseUrl : String = Const.BASE_URL
        ) : Api {
            val token = SessionManager.getAuthorizationToken()
            if (token.isNotEmpty()) {
                tokenData = token
            }
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        chain.proceed(chain.request().newBuilder().also {
                            if(!tokenData.isNullOrEmpty())
                            it.addHeader("Authorization", "Bearer $tokenData")
                            it.addHeader("Content-Type", "application/json")
                            it.addHeader("Cache-Control", "no-cache")
                        }.build())
                    }.also { client ->
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(api)
        }

    }






}