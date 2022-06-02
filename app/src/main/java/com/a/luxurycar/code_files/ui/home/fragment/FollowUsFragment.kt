package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.FollowUsRepository
import com.a.luxurycar.code_files.view_model.FollowUsViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentFollowUsBinding
import kotlinx.android.synthetic.main.fragment_seller_home.*


class FollowUsFragment : BaseFragment<FollowUsViewModel,FragmentFollowUsBinding,FollowUsRepository>() {

   override fun getViewModel()=FollowUsViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentFollowUsBinding.inflate(inflater,container,false)
    override fun getRepository() = FollowUsRepository(ApiAdapter.buildApi(ApiService::class.java))
}