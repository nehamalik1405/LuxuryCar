package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.CarListRepository
import com.a.luxurycar.code_files.ui.home.adapter.SearchCarAdapter
import com.a.luxurycar.code_files.ui.home.model.car_list_response.DataX
import com.a.luxurycar.code_files.ui.home.model.search_ads_response.SearchData
import com.a.luxurycar.code_files.view_model.CarListViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.utils.HelperClass
import com.a.luxurycar.databinding.FragmentCarListBinding
import com.a.luxurycar.databinding.FragmentCarSearchBinding

class CarSearchFragment :
    BaseFragment<CarListViewModel, FragmentCarSearchBinding, CarListRepository>() {

    var arrSearchCarList: ArrayList<SearchData> = ArrayList()

    override fun getViewModel() = CarListViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentCarSearchBinding.inflate(inflater, container, false)

    override fun getRepository() = CarListRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    //    arrSearchCarList = arguments?.getSerializable("searchList") as ArrayList<SearchData>
        if( HelperClass.arrSearchData.size>0)
        setRecylerView()

    }

    private fun setRecylerView() {
        val adapter = SearchCarAdapter(this,HelperClass.arrSearchData)
        binding.recyclerviewSearchCarList.adapter = adapter
    }

    fun navigateToDetailPage(id: String) {
        val bundle = Bundle()
        bundle.putString("product_detail_id", id)
        findNavController().navigate(com.a.luxurycar.R.id.productDetailFragment, bundle)
    }


}