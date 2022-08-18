package com.a.luxurycar.code_files.ui.home.fragment

import android.R
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.CarListRepository
import com.a.luxurycar.code_files.ui.home.adapter.CarListAdapter
import com.a.luxurycar.code_files.ui.home.model.car_list_response.DataX
import com.a.luxurycar.code_files.view_model.CarListViewModel
import com.a.luxurycar.common.helper.AdapterSpinner
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.Utils
import com.a.luxurycar.common.utils.handleApiErrors
import com.a.luxurycar.common.utils.visible
import com.a.luxurycar.databinding.FragmentCarListBinding

class CarListFragment :BaseFragment<CarListViewModel, FragmentCarListBinding,CarListRepository>(){

lateinit var arrCarList:ArrayList<DataX>
lateinit var arrSortByListHashMap:ArrayList<HashMap<String,String>>

    override fun getViewModel()=CarListViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentCarListBinding.inflate(inflater,container,false)
    override fun getRepository() = CarListRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arrCarList = arrayListOf()
        arrSortByListHashMap = ArrayList()
        setSpinnerItem()
        callCarListRequestApi()
        observeCarListResponse()
        setSpinnerAdapterAndDropdown()


    }

    private fun setSpinnerAdapterAndDropdown() {
        val adapterSortBy = AdapterSpinner(
            requireContext(),
            R.layout.simple_spinner_item,
            arrSortByListHashMap
        )
        //Drop down layout style - list view for year
        adapterSortBy.setDropDownViewResource(com.a.luxurycar.R.layout.simple_spinner_drop_down_item)
        //attaching data adapter to spinner
        binding.spinnerSelectedFeatured.setAdapter(adapterSortBy)
    }

    private fun setSpinnerItem() {
        val sortByList = listOf("All","Price","Latest")

        for (item in sortByList){
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "")
            hashMap.put(Const.KEY_NAME, item)
            //add default item
            arrSortByListHashMap.add(hashMap)
        }

    }

    private fun observeCarListResponse() {
        viewModel.getCarListResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarCarList.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {

                    if (it.values != null && it.values.status == 1) {
                        arrCarList = it.values.data.data
                        setCarListAdapter()
                    }


                    if (!Utils.isEmptyOrNull(it.values.message)) {
                        Toast.makeText(
                            requireContext(),
                            it.values.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                is Resource.Failure -> handleApiErrors(it)

            }
        })
    }

    private fun setCarListAdapter() {
        val carListAdapter = CarListAdapter(requireContext(),arrCarList)
        binding.recyclerviewCarList.adapter = carListAdapter
    }

    private fun callCarListRequestApi() {

        val bundle = arguments
        var id = ""

        if (bundle != null) {
            id = bundle.getString("id").toString()
            val topTextViewName = bundle.getString("list_name").toString()
            if(topTextViewName != null){
                binding.topTextViewName.text = topTextViewName
            }
        }
        viewModel.getCarListResponse(id)

    }

}