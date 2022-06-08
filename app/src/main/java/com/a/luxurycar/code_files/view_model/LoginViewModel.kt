package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.LoginRepository
import com.a.luxurycar.code_files.ui.auth.model.login.LoginResponse
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class LoginViewModel(val repository: LoginRepository) : BaseViewModel(repository){

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    fun getLoginResponse(body: RequestBody) = viewModelScope.launch {

        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.getLoginResponse(body)

    }

}