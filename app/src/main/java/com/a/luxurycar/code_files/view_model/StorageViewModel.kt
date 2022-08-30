package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.StorageRepository
import com.a.luxurycar.code_files.ui.home.model.cms.CmsResponsee
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch

class StorageViewModel(val repository:StorageRepository):BaseViewModel(repository) {

    private val _cmsResponse: MutableLiveData<Resource<CmsResponsee>> = MutableLiveData()
    val cmsResponse: LiveData<Resource<CmsResponsee>>
        get() = _cmsResponse

    fun getCmsResponse() = viewModelScope.launch {
        _cmsResponse.value = Resource.Loading
        _cmsResponse.value = repository.getCmsResponse()

    }
}