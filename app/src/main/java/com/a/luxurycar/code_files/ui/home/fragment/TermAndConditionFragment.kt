package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.TermAndConditionRepository
import com.a.luxurycar.code_files.view_model.TermAndConditionViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentTermAndConditionBinding

class TermAndConditionFragment : BaseFragment<TermAndConditionViewModel,FragmentTermAndConditionBinding,TermAndConditionRepository>() {



    override fun getViewModel()=TermAndConditionViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentTermAndConditionBinding.inflate(inflater,container,false)

    override fun getRepository()= TermAndConditionRepository(ApiAdapter.buildApi(ApiService::class.java))


}