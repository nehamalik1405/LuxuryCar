package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.ChangePasswordRepository
import com.a.luxurycar.code_files.ui.auth.model.register.RegistrationResponse
import com.a.luxurycar.code_files.ui.home.model.change_password.ChangePasswordModel
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class ChangePasswordViewModel(val repository:ChangePasswordRepository):BaseViewModel(repository) {

    private val _changePasswordResponse: MutableLiveData<Resource<ChangePasswordModel>> = MutableLiveData()
    val changePasswordResponse: LiveData<Resource<ChangePasswordModel>>
        get() = _changePasswordResponse

    fun getRegisterResponse(body: RequestBody) = viewModelScope.launch {

        _changePasswordResponse.value = Resource.Loading
        _changePasswordResponse.value = repository.getChangePasswordResponse(body)

    }

}