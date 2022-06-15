package com.a.luxurycar.code_files.ui.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.BuyerRegistrationRepository
import com.a.luxurycar.code_files.view_model.BuyerRegistrationViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.databinding.FragmentRegisterBuyerAndSellerBinding


class RegistrationType :  BaseFragment<BuyerRegistrationViewModel, FragmentRegisterBuyerAndSellerBinding, BuyerRegistrationRepository>() {

    lateinit var bundle: Bundle
    val buyer = "Customer"
    val seller = "Seller"

    override fun getViewModel() = BuyerRegistrationViewModel::class.java


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentRegisterBuyerAndSellerBinding.inflate(inflater, container, false)

    override fun getRepository() =
        BuyerRegistrationRepository(ApiAdapter.buildApi(ApiService::class.java))


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageClickListeners()
    }

    private fun manageClickListeners() {

        binding.btnBuyer.setOnClickListener {
            bundle = Bundle()
            bundle.putString(Const.KEY_TYPE, buyer)
            findNavController().navigate(R.id.loginFragment,bundle)
        }

        binding.btnSeller.setOnClickListener {
            bundle = Bundle()
            bundle.putString(Const.KEY_TYPE, seller)
            findNavController().navigate(R.id.loginFragment,bundle)
        }

    }

}