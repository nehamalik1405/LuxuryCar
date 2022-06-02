package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.AdvertiseWithUsRepository
import com.a.luxurycar.code_files.view_model.AdvertiseWithUsViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentAdvertiseWithUsBinding


class AdvertiseWithUsFragment : BaseFragment<AdvertiseWithUsViewModel,FragmentAdvertiseWithUsBinding,AdvertiseWithUsRepository>() {

    override fun getViewModel() = AdvertiseWithUsViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )=FragmentAdvertiseWithUsBinding.inflate(inflater,container,false)

    override fun getRepository() = AdvertiseWithUsRepository(ApiAdapter.buildApi(ApiService::class.java))
}