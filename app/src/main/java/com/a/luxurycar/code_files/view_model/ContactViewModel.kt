package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.ContactRepository
import com.a.luxurycar.code_files.ui.home.model.contactus.ContactUsResponse
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class ContactViewModel(val repository:ContactRepository):BaseViewModel(repository) {

    private val _contactusResponse: MutableLiveData<Resource<ContactUsResponse>> = MutableLiveData()
    val contactusResponse: LiveData<Resource<ContactUsResponse>>
        get() = _contactusResponse

    fun getContactUsResponse(body: RequestBody) = viewModelScope.launch {
        _contactusResponse.value = Resource.Loading
        _contactusResponse.value = repository.getContactUSResponse(body)

    }
}