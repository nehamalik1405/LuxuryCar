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
import com.a.luxurycar.common.requestresponse.Const.Companion.KEY_BUYER
import com.a.luxurycar.common.requestresponse.Const.Companion.KEY_SELLER
import com.a.luxurycar.databinding.FragmentBuyerProfileDetailBinding


class BuyerProfileDetailFragment :
    BaseFragment<BuyerMyProfileViewModel, FragmentBuyerProfileDetailBinding, BuyerMyProfileRepository>() {
    override fun getViewModel() = BuyerMyProfileViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentBuyerProfileDetailBinding.inflate(inflater, container, false)

    override fun getRepository() =
        BuyerMyProfileRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMyProfileDetail()
    }

    private fun setMyProfileDetail() {


        if (SessionManager.isUserLoggedIn()) {
            val userData = SessionManager.getUserData()

            if (userData?.role.equals(KEY_BUYER)) {

                val firstName = userData!!.firstname
                val lastName = userData.lastname
                val email = userData.email
                val phone = userData.phone

                if (firstName != null && lastName != null && email != null && phone != null) {
                    binding.txtViewShowUserName.text = firstName
                    binding.txtViewShowUserSurName.text = lastName
                    binding.txtViewShowUserEmail.text = email
                    binding.txtViewShowUserMobileNumber.text = phone

                }

            } else if (userData?.role.equals(KEY_SELLER)) {

                val companyName = userData!!.companyName
                val email = userData.email
                val phone = userData.phone

                binding.txtViewName.text = "Company"
                binding.txtViewShowUserSurName.visibility = View.GONE
                binding.txtViewSurName.visibility = View.GONE

                if (companyName != null && email != null && phone != null) {
                    binding.txtViewShowUserName.text = companyName
                    binding.txtViewShowUserEmail.text = email
                    binding.txtViewShowUserMobileNumber.text = phone

                }

            }


        }


    }
}