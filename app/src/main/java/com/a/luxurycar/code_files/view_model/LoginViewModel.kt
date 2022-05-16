package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.LoginRepository
import com.a.luxurycar.code_files.ui.auth.model.LoginResponse
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class LoginViewModel(val repository: LoginRepository) : BaseViewModel(repository){

    private val _LoginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val LoginResponse: LiveData<Resource<LoginResponse>>
        get() = _LoginResponse

    fun getLoginResponse(body: RequestBody) = viewModelScope.launch {

        _LoginResponse.value = Resource.Loading
        _LoginResponse.value = repository.getLoginResponse(body)

    }

}