package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.UpdateDetailRepository
import com.a.luxurycar.code_files.ui.home.model.update_details.UpdateDetailsModel
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class UpdateDetailViewModel(val repository: UpdateDetailRepository):BaseViewModel(repository) {

    //login response
    private val _UpdateDetailResponse: MutableLiveData<Resource<UpdateDetailsModel>> = MutableLiveData()
    val UpdateDetailResponse: LiveData<Resource<UpdateDetailsModel>>
        get() = _UpdateDetailResponse

    fun getUpdateDetails(firstName:String,lastName:String,email:String,phone:String) = viewModelScope.launch {

        _UpdateDetailResponse.value = Resource.Loading
        _UpdateDetailResponse.value = repository.getUpdateDetails(firstName,lastName,email,phone)

    }
}