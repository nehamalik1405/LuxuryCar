package com.a.luxurycar.code_files.ui.seller_deshboard.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.SellerRepository
import com.a.luxurycar.code_files.view_model.SellerViewModel
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentMyProfileDetailBinding


class MyProfileDetailFragment : BaseFragment<SellerViewModel,FragmentMyProfileDetailBinding,SellerRepository>() {
    override fun getViewModel()=SellerViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentMyProfileDetailBinding.inflate(inflater,container,false)

    override fun getRepository()= SellerRepository(ApiAdapter.buildApi(ApiService::class.java))
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMyProfileDetail()
    }

    private fun setMyProfileDetail() {

        val companyName = SessionManager.getCompanyName()
        val email = SessionManager.getEmail()
        val phone = SessionManager.getPhone()

            binding.txtViewShowCompnayName.text = companyName.toString()
            binding.txtViewShowUserEmail.text = email.toString()
            binding.txtViewShowUserMobileNumber.text = phone.toString()

    }

}