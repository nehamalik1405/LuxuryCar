package com.a.luxurycar.code_files.ui.add_car.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.AddCarRepository
import com.a.luxurycar.code_files.view_model.AddCarViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentAddCarStepThreeBinding


class AddCarStepThree :BaseFragment<AddCarViewModel, FragmentAddCarStepThreeBinding, AddCarRepository>() {

    override fun getViewModel()=AddCarViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentAddCarStepThreeBinding.inflate(inflater,container,false)
    override fun getRepository()= AddCarRepository(ApiAdapter.buildApi(ApiService::class.java))

}