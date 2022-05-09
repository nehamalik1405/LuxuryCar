package com.a.luxurycar.code_files.base;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseRepository
import com.a.luxurycar.common.requestresponse.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.math.BigInteger

abstract class BaseViewModel (
    private val repository : BaseRepository
) : ViewModel() {

    suspend fun logout(api : ApiService)  = withContext(Dispatchers.IO) {
        repository.logout(api)
    }

}
