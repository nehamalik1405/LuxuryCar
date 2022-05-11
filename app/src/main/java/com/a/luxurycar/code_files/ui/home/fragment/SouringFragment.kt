package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.adapter.ImageAdapter
import com.a.luxurycar.code_files.ui.home.adapter.SouceSliderAdapter
import com.a.luxurycar.code_files.ui.home.adapter.SourceListAdapter
import com.a.luxurycar.databinding.FragmentSouringBinding
import com.google.android.material.tabs.TabLayoutMediator


class SouringFragment : Fragment() {

    var _binding: FragmentSouringBinding? = null
    val binding get() = _binding!!;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSouringBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
        setRecyclerViewList()
    }

    private fun setRecyclerViewList() {
        val sourceListAdapter = SourceListAdapter()
        binding.recyclerViewSourceList.adapter= sourceListAdapter
        binding.recyclerViewSourceList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)

    }

    private fun setViewPager() {
        val souceSliderAdapter = SouceSliderAdapter()
        binding.viewpagerSourcingDataSteps.adapter= souceSliderAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewpagerSourcingDataSteps) { tab, position -> }.attach()
    }

}