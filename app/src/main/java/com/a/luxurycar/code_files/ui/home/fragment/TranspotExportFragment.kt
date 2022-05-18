package com.a.luxurycar.code_files.ui.home.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.TransportExportRepository
import com.a.luxurycar.code_files.ui.home.adapter.ExpandableListAdapter
import com.a.luxurycar.code_files.ui.home.adapter.TransportExportAdapter
import com.a.luxurycar.code_files.view_model.TransportExportViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.databinding.FragmentTranspotExportBinding


class TranspotExportFragment : BaseFragment<TransportExportViewModel,FragmentTranspotExportBinding,TransportExportRepository>() {



    override fun getViewModel()=TransportExportViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentTranspotExportBinding.inflate(inflater,container,false)

    override fun getRepository()=TransportExportRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setExpandableCardRecyclerView()

    }

    private fun setExpandableCardRecyclerView() {
        val expandableListAdapter = ExpandableListAdapter()
        binding.recyclerviewTransportExportExpandableList.adapter = expandableListAdapter
        binding.recyclerviewTransportExportExpandableList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }

    private fun setRecyclerView() {
        val transportExportAdapter = TransportExportAdapter()
        binding.recyclerviewTransportExportList.adapter = transportExportAdapter
        binding.recyclerviewTransportExportList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)

    }
}