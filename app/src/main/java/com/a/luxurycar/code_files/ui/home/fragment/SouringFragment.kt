package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.SourcingRepository
import com.a.luxurycar.code_files.ui.home.adapter.SouceSliderAdapter
import com.a.luxurycar.code_files.ui.home.adapter.SourceListAdapter
import com.a.luxurycar.code_files.ui.home.model.cms.CmsbannerContent
import com.a.luxurycar.code_files.ui.home.model.cms.Sourcing
import com.a.luxurycar.code_files.view_model.SourcingViewModel
import com.a.luxurycar.common.helper.NetworkHelper
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.handleApiErrors
import com.a.luxurycar.common.utils.toast
import com.a.luxurycar.common.utils.visible
import com.a.luxurycar.databinding.FragmentSouringBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso


class SouringFragment :
    BaseFragment<SourcingViewModel, FragmentSouringBinding, SourcingRepository>() {

    lateinit var sourcingModel: Sourcing

    var arrcmsbannerContent: ArrayList<CmsbannerContent> = ArrayList()

    override fun getViewModel() = SourcingViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentSouringBinding.inflate(inflater, container, false)

    override fun getRepository() = SourcingRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // setViewPager()
        // setRecyclerViewList()
        manageClickListener()
        callCmsApi()
    }

    private fun callCmsApi() {
        cmsApi()
        viewModel.cmsResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {

                        if (it.values.data?.sourcing != null) {
                            sourcingModel = it.values.data?.sourcing
                        }

                        binding.btnSourceMyCar.visibility = View.VISIBLE
                        binding.btnSourceMyCar2.visibility = View.VISIBLE
                        binding.cardViewCarImage2.visibility = View.VISIBLE

                        if (!sourcingModel.cmsBanner?.cmsbannerContent?.isEmpty()!!) {
                            arrcmsbannerContent =
                                sourcingModel.cmsBanner?.cmsbannerContent as ArrayList<CmsbannerContent>
                            setViewPager()
                        }

                        sourcingModel.cmsBanner?.image?.let {
                            binding.cardViewCarImage.visibility = View.VISIBLE
                            Picasso.get().load(it).into(binding.imgViewBottomCar)
                        }

                        sourcingModel.cmsBanner?.title?.let {
                            binding.txtViewHowdoesitwork.visibility = View.VISIBLE
                            binding.txtViewHowdoesitwork.text = it
                        }

                        sourcingModel.content?.let {
                            binding.txtViewWhatIsCarSourcing.visibility = View.VISIBLE
                            binding.txtViewWhatIsCarSourcing.text =
                                Html.fromHtml(it).toString()
                        }

                        setRecyclerViewList()

                    } else {
                        requireContext().toast(it?.values?.message!!)
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

    private fun manageClickListener() {
        binding.btnSourceMyCar.setOnClickListener {
            findNavController().navigate(R.id.nav_saurce_my_car)
        }

        binding.btnSourceMyCar2.setOnClickListener {
            findNavController().navigate(R.id.nav_saurce_my_car)
        }
    }

    private fun setRecyclerViewList() {
        val sourceListAdapter = SourceListAdapter()
        binding.recyclerViewSourceList.adapter = sourceListAdapter
    }

    private fun setViewPager() {
        val souceSliderAdapter = SouceSliderAdapter(arrcmsbannerContent)
        binding.viewpagerSourcingDataSteps.adapter = souceSliderAdapter

        TabLayoutMediator(binding.tabLayout,
            binding.viewpagerSourcingDataSteps) { tab, position -> }.attach()
    }


}