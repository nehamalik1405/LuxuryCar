package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.AuthRepository
import com.a.luxurycar.code_files.ui.auth.model.login.LoginResponse
import com.a.luxurycar.code_files.ui.auth.model.register.RegistrationResponse
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class AuthViewModel(val repository: AuthRepository) : BaseViewModel(repository) {

    //login response
    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val LoginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    fun getLoginResponse(body: RequestBody) = viewModelScope.launch {

        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.getLoginResponse(body)
    }

    // registration response
    private val _registerResponse: MutableLiveData<Resource<RegistrationResponse>> = MutableLiveData()
    val RegisterResponse: LiveData<Resource<RegistrationResponse>>
        get() = _registerResponse

    fun getRegisterResponse(body: RequestBody) = viewModelScope.launch {

        _registerResponse.value = Resource.Loading
        _registerResponse.value = repository.getRegisterResponse(body)

    }



}