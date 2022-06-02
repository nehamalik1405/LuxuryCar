package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.SourcingRepository
import com.a.luxurycar.code_files.ui.home.adapter.ImageAdapter
import com.a.luxurycar.code_files.ui.home.adapter.SouceSliderAdapter
import com.a.luxurycar.code_files.ui.home.adapter.SourceListAdapter
import com.a.luxurycar.code_files.view_model.SourcingViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentSouringBinding
import com.google.android.material.tabs.TabLayoutMediator


class SouringFragment :
    BaseFragment<SourcingViewModel, FragmentSouringBinding, SourcingRepository>() {

    override fun getViewModel() = SourcingViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentSouringBinding.inflate(inflater, container, false)
    override fun getRepository() = SourcingRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
        setRecyclerViewList()
    }

    private fun setRecyclerViewList() {
        val sourceListAdapter = SourceListAdapter()
        binding.recyclerViewSourceList.adapter = sourceListAdapter
    }

    private fun setViewPager() {
        val souceSliderAdapter = SouceSliderAdapter()
        binding.viewpagerSourcingDataSteps.adapter = souceSliderAdapter

        TabLayoutMediator(binding.tabLayout,
            binding.viewpagerSourcingDataSteps) { tab, position -> }.attach()
    }


}