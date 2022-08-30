package com.a.luxurycar.code_files.ui.home.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.TransportExportRepository
import com.a.luxurycar.code_files.ui.home.adapter.ExpandableListAdapter
import com.a.luxurycar.code_files.ui.home.adapter.TransportExportAdapter
import com.a.luxurycar.code_files.ui.home.model.cms.CmsbannerContent
import com.a.luxurycar.code_files.ui.home.model.faqresponse.Data
import com.a.luxurycar.code_files.view_model.TransportExportViewModel
import com.a.luxurycar.common.helper.NetworkHelper
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.handleApiErrors
import com.a.luxurycar.common.utils.toast
import com.a.luxurycar.common.utils.visible
import com.a.luxurycar.databinding.FragmentTranspotExportBinding


class TranspotExportFragment : BaseFragment<TransportExportViewModel,FragmentTranspotExportBinding,TransportExportRepository>() {

     var arrListFaq = ArrayList<Data>()
     var arrListStepsList = ArrayList<CmsbannerContent>()

    override fun getViewModel()=TransportExportViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentTranspotExportBinding.inflate(inflater,container,false)

    override fun getRepository()=TransportExportRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callFaqListApi()
        callCmsApi()
    }

    private fun callCmsApi() {
        cmsApi()

        viewModel.cmsResponse.observe(viewLifecycleOwner, Observer {
             binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        if(!it.values.data?.transportExport?.cmsBanner?.cmsbannerContent?.isNullOrEmpty()!!){
                            arrListStepsList=it.values.data?.transportExport?.cmsBanner?.cmsbannerContent!! as ArrayList<CmsbannerContent> /* = java.util.ArrayList<com.a.luxurycar.code_files.ui.home.model.cms_response.CmsbannerContent> */
                            setRecyclerView()
                            binding.rootView.visibility =View.VISIBLE
                        }
                    } else {
                        requireContext().toast(it.values.message!!)
                    }
                }
                is Resource.Failure -> handleApiErrors(it)
                else -> {}
            }
        })
    }

    private fun cmsApi() {
        if (NetworkHelper.isNetworkAvaialble(requireContext())) {
            viewModel.getCmsResponse()
        } else {
            requireContext().toast("No Internet Connection")
        }

    }

    private fun callFaqListApi() {
        faqListApi()
        viewModel.faqListResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                       if(!it.values.data.isEmpty()){
                           arrListFaq=it.values.data as  ArrayList<Data>
                           setExpandableCardRecyclerView()
                       }
                    } else {
                        requireContext().toast(it.values.message)
                    }
                }
                is Resource.Failure -> handleApiErrors(it)
                else -> {}
            }
        })

    }

    private fun faqListApi() {
        if (NetworkHelper.isNetworkAvaialble(requireContext())) {
            viewModel.getFaqListResponse()
        } else {
            requireContext().toast("No Internet Connection")
        }
    }

    private fun setExpandableCardRecyclerView() {
        val expandableListAdapter = ExpandableListAdapter(arrListFaq)
        binding.recyclerviewTransportExportExpandableList.adapter = expandableListAdapter
        binding.recyclerviewTransportExportExpandableList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun setRecyclerView() {
        val transportExportAdapter = TransportExportAdapter(requireContext(),arrListStepsList)
        binding.recyclerviewTransportExportList.adapter = transportExportAdapter
        binding.recyclerviewTransportExportList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)

    }
}