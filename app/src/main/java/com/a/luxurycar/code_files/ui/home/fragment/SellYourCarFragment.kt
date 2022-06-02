package com.a.luxurycar.code_files.ui.home.fragment


import android.view.LayoutInflater
import android.view.ViewGroup
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.SellYourCarRepository
import com.a.luxurycar.code_files.view_model.SellYourCarViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentSellYourCarBinding


class SellYourCarFragment : BaseFragment<SellYourCarViewModel, FragmentSellYourCarBinding, SellYourCarRepository>() {

    override fun getViewModel() = SellYourCarViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentSellYourCarBinding.inflate(inflater,container,false)
    override fun getRepository()= SellYourCarRepository(ApiAdapter.buildApi(ApiService::class.java))


}