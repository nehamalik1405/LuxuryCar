package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.AboutUsRepository
import com.a.luxurycar.code_files.view_model.AboutUsViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentAboutUsBinding

class AboutUsFragment : BaseFragment<AboutUsViewModel,FragmentAboutUsBinding,AboutUsRepository>() {

    override fun getViewModel()= AboutUsViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentAboutUsBinding.inflate(inflater,container,false)

    override fun getRepository() = AboutUsRepository(ApiAdapter.buildApi(ApiService::class.java))

}