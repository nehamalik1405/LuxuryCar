/*
package com.near.common.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
    context: Context
) {

    private val applicationContext = context.applicationContext
    private val dataStore : DataStore<Preferences>

    private val USER_PREFERENCES_NAME = "user_preferences"

    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    val authToken : Flow<String?>
    get() = dataStore.data.map { preferences ->
        preferences[KEY_AUTH]
    }

    val userName : Flow<String?>
    get() = dataStore.data.map { preferences ->
        preferences[KEY_USER_NAME]
    }

    val userID : Flow<Int?>
    get() = dataStore.data.map { preferences ->
        preferences[KEY_USER_ID]
    }

    val userTypeId : Flow<Int?>
    get() = dataStore.data.map { preferences ->
        preferences[KEY_USER_TYPE_ID]
    }

    val companyId : Flow<Int?>
    get() = dataStore.data.map { preferences ->
        preferences[KEY_COMPANY_ID]
    }

    //sushant
   // val allTaskModel : Flow<ArrayList<Data>?>
    //get() = dataStore.data.map { preferences ->  preferences[KEY_ALL_TASK_MODEL]}

    suspend fun saveAuthToken(
        authToken : String
    ) {
        dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }

    suspend fun saveUserId(
        userId : Int
    ) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = userId
        }
    }

    suspend fun saveCompanyId(
        companyId : Int
    ) {
        dataStore.edit { preferences ->
            preferences[KEY_COMPANY_ID] = companyId
        }
    }

    suspend fun saveUserName(
        userName : String
    ) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_NAME] = userName
        }
    }

    suspend fun saveUserTypeId(
        userTypeId : Int
    ) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_TYPE_ID] = userTypeId
        }
    }

    suspend fun clear() {

        dataStore.edit { preferences ->
            preferences.clear()
        }

    }

    companion object {

        private val KEY_AUTH = preferencesKey<String>("key_auth")
        private val KEY_USER_NAME = preferencesKey<String>("key_user_name")
        private val KEY_USER_ID = preferencesKey<Int>("user_id")
        private val KEY_USER_TYPE_ID = preferencesKey<Int>("user_type_id")
        private val KEY_COMPANY_ID = preferencesKey<Int>("company_id")

        //private val KEY_ALL_TASK_MODEL = preferencesKey<ArrayList<Data>>("all_task_model")
    }



}*/
