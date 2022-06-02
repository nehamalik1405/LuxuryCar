package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.LanguageRepository
import com.a.luxurycar.code_files.view_model.LanguageViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentLanguageBinding
import com.a.luxurycar.databinding.FragmentLoginBinding

class LanguageFragment : BaseFragment<LanguageViewModel,FragmentLanguageBinding,LanguageRepository>() {


   override fun getViewModel() = LanguageViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )=FragmentLanguageBinding.inflate(inflater,container,false)

    override fun getRepository()= LanguageRepository(ApiAdapter.buildApi(ApiService::class.java))

}