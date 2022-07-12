package com.a.luxurycar.code_files.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.a.luxurycar.code_files.base.BaseViewModel
import com.a.luxurycar.code_files.repository.AddCarStepThreeRepository
import com.a.luxurycar.code_files.ui.add_car.model.step_three_listing_plan.AddCarStepThreeListingPlanModel
import com.a.luxurycar.common.requestresponse.Resource
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class AddCarStepThreeViewModel(val repository: AddCarStepThreeRepository):BaseViewModel(repository) {

    private val _getAddSellerListingPlanResponse: MutableLiveData<Resource<AddCarStepThreeListingPlanModel>> = MutableLiveData()
    val getAddSellerListingPlanResponse: LiveData<Resource<AddCarStepThreeListingPlanModel>>
        get() = _getAddSellerListingPlanResponse

    fun getAddSellerListingPlan() = viewModelScope.launch {

        _getAddSellerListingPlanResponse.value = Resource.Loading
        _getAddSellerListingPlanResponse.value = repository.getAddSellerListingPlan()

    }

    fun getAddSellerPlan(body: RequestBody)= viewModelScope.launch {
        repository.getAddSellerPlan(body)
    }

}