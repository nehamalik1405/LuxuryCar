package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.NewEnquiryFormRepository
import com.a.luxurycar.code_files.view_model.NewEnquiryFormViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentNewEnquiryFormStorageBinding


class NewEnquiryFormStorageFragment : BaseFragment<NewEnquiryFormViewModel,FragmentNewEnquiryFormStorageBinding,NewEnquiryFormRepository>() {


    override fun getViewModel() = NewEnquiryFormViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentNewEnquiryFormStorageBinding.inflate(inflater,container,false)

    override fun getRepository()= NewEnquiryFormRepository(ApiAdapter.buildApi(ApiService::class.java))
}