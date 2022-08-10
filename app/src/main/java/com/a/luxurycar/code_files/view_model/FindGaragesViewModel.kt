package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.FindGaragesRepository
import com.a.luxurycar.code_files.ui.seller_deshboard.model.find_garages.FindGaragesResponse
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch

class FindGaragesViewModel(val repository:FindGaragesRepository):BaseViewModel(repository) {

    private val _getFindGaragesResponse: MutableLiveData<Resource<FindGaragesResponse>> = MutableLiveData()
    val getFindGaragesResponse: LiveData<Resource<FindGaragesResponse>>
        get() = _getFindGaragesResponse

    fun getFindGaragesResponse() = viewModelScope.launch {
        _getFindGaragesResponse.value = Resource.Loading
        _getFindGaragesResponse.value = repository.getFindGaragesResponse()

    }

}