package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.ChangePasswordRepository
import com.a.luxurycar.code_files.repository.ForgotPasswordRepository
import com.a.luxurycar.code_files.ui.auth.model.forgot_password.SendOtpModel
import com.a.luxurycar.code_files.ui.auth.model.forgot_password.VerifyOtpModel
import com.a.luxurycar.code_files.ui.home.model.change_password.ChangePasswordModel
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class ForgotPasswordViewModel(val repository: ForgotPasswordRepository): BaseViewModel(repository) {

    // send otp viewmodel
    private val _SendOtpResponse: MutableLiveData<Resource<SendOtpModel>> = MutableLiveData()
    val SendOtpResponse: LiveData<Resource<SendOtpModel>>
        get() = _SendOtpResponse

    fun getSendOtpResponse(body: RequestBody) = viewModelScope.launch {

        _SendOtpResponse.value = Resource.Loading
        _SendOtpResponse.value = repository.getSendOtpResponse(body)

    }

    // verify otp viewmodel
    private val _VerifyOtpResponse: MutableLiveData<Resource<VerifyOtpModel>> = MutableLiveData()
    val VerifyOtpResponse: LiveData<Resource<VerifyOtpModel>>
        get() = _VerifyOtpResponse

    fun getVerifyOtpResponse(body: RequestBody) = viewModelScope.launch {

        _VerifyOtpResponse.value = Resource.Loading
        _VerifyOtpResponse.value = repository.getVerifyResponse(body)

    }
}