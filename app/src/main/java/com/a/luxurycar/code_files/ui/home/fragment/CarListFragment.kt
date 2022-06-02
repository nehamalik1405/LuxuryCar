package com.a.luxurycar.code_files.ui.home.fragment

import android.app.Fragment
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.CarListRepository
import com.a.luxurycar.code_files.ui.home.adapter.AdvertiserCarListAdapter
import com.a.luxurycar.code_files.ui.home.adapter.CarListAdapter
import com.a.luxurycar.code_files.view_model.CarListViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentCarListBinding

class CarListFragment :BaseFragment<CarListViewModel, FragmentCarListBinding,CarListRepository>(){


    override fun getViewModel()=CarListViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentCarListBinding.inflate(inflater,container,false)
    override fun getRepository() = CarListRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val carListAdapter = CarListAdapter(requireContext())
        binding.recyclerviewCarList.adapter = carListAdapter
    }

}