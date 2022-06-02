package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.InspectingRepository
import com.a.luxurycar.code_files.view_model.InspectingViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentInspectingBinding

class InspectingFragment : BaseFragment<InspectingViewModel,FragmentInspectingBinding,InspectingRepository>() {


    override fun getViewModel() = InspectingViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentInspectingBinding.inflate(inflater,container,false)
    override fun getRepository() = InspectingRepository(ApiAdapter.buildApi(ApiService::class.java))


}