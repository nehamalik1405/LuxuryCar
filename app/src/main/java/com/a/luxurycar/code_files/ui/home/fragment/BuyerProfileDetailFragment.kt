package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.BuyerMyProfileRepository
import com.a.luxurycar.code_files.view_model.BuyerMyProfileViewModel
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentBuyerProfileDetailBinding


class BuyerProfileDetailFragment : BaseFragment<BuyerMyProfileViewModel,FragmentBuyerProfileDetailBinding,BuyerMyProfileRepository>() {
    override fun getViewModel()= BuyerMyProfileViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentBuyerProfileDetailBinding.inflate(inflater,container,false)

    override fun getRepository()= BuyerMyProfileRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMyProfileDetail()
    }

    private fun setMyProfileDetail() {


        val firstName = SessionManager.getFirstName()
        val lastName = SessionManager.getLastName()
        val email = SessionManager.getEmail()
        val phone = SessionManager.getPhone()
        if (firstName != null && lastName != null && email != null && phone != null) {
            binding.txtViewShowUserName.text = firstName
            binding.txtViewShowUserSurName.text = lastName
            binding.txtViewShowUserEmail.text = email
            binding.txtViewShowUserMobileNumber.text = phone

        }
    }
}