package com.a.luxurycar.code_files.ui.seller_deshboard.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.PaymentHistoryRepository
import com.a.luxurycar.code_files.ui.seller_deshboard.adapter.PaymentHistoryAdapter
import com.a.luxurycar.code_files.view_model.PaymentHistoryViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentSellerPaymentHistoryBinding


class SellerPaymentHistoryFragment : BaseFragment<PaymentHistoryViewModel, FragmentSellerPaymentHistoryBinding,PaymentHistoryRepository>() {
    override fun getViewModel()=PaymentHistoryViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentSellerPaymentHistoryBinding.inflate(inflater,container,false)


    override fun getRepository()=PaymentHistoryRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPaymentHistoryList()
    }

    private fun setPaymentHistoryList() {
        val paymentHistoryAdapter = PaymentHistoryAdapter()
        binding.recyclerViewPaymentHistory.adapter = paymentHistoryAdapter
    }


}