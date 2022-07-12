package com.a.luxurycar.code_files.ui.seller_deshboard.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.SellerHomeRepository
import com.a.luxurycar.code_files.ui.add_car.AddCarActivity
import com.a.luxurycar.code_files.ui.home.HomeActivity
import com.a.luxurycar.code_files.ui.seller_deshboard.adapter.ForRentAdapter
import com.a.luxurycar.code_files.ui.seller_deshboard.adapter.ForSaleAdapter
import com.a.luxurycar.code_files.view_model.SellerHomeViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.utils.StartActivity
import com.a.luxurycar.databinding.FragmentSellerHomeBinding

class SellerHomeFragment : BaseFragment<SellerHomeViewModel,FragmentSellerHomeBinding,SellerHomeRepository>() {

    override fun getViewModel() =SellerHomeViewModel::class.java

        override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentSellerHomeBinding.inflate(inflater,container,false)

    override fun getRepository() = SellerHomeRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageClickListener()
        setForSaleList()
        setForRentList()
    }

    private fun setForRentList() {
      val forSaleAdapter = ForSaleAdapter()
        binding.recyclerViewForSale.adapter = forSaleAdapter
        binding.recyclerViewForSale.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)
    }

    private fun setForSaleList() {
        val forRentAdapter = ForRentAdapter()
        binding.recyclerViewForRent.adapter = forRentAdapter
        binding.recyclerViewForRent.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)
    }

    private fun manageClickListener() {
        binding.conLayoutFabButton.setOnClickListener {
            StartActivity(AddCarActivity::class.java)
           // requireActivity().finishAffinity()
        }

        binding.consLayoutTabForSale.setOnClickListener {
            binding.consLayoutTabForRent.setBackgroundResource(0)
            binding.consLayoutTabForBuyerEnqukles.setBackgroundResource(0)
            binding.consLayoutTabForSale.setBackgroundResource(R.drawable.drawable_tab_background)
            binding.recyclerViewForSale.visibility = View.VISIBLE
            binding.recyclerViewForRent.visibility = View.GONE

        }
        binding.consLayoutTabForRent.setOnClickListener {
            binding.recyclerViewForSale.visibility = View.GONE
            binding.recyclerViewForRent.visibility = View.VISIBLE
            binding.consLayoutTabForSale.setBackgroundResource(0)
            binding.consLayoutTabForBuyerEnqukles.setBackgroundResource(0)
            binding.consLayoutTabForRent.setBackgroundResource(R.drawable.drawable_tab_background)

        }
        binding.consLayoutTabForBuyerEnqukles.setOnClickListener {
            binding.consLayoutTabForRent.setBackgroundResource(0)
            binding.consLayoutTabForSale.setBackgroundResource(0)
            binding.consLayoutTabForBuyerEnqukles.setBackgroundResource(R.drawable.drawable_tab_background)
        }
    }



}