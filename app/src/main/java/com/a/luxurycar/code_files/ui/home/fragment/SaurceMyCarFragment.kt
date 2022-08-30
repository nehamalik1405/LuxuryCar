package com.a.luxurycar.code_files.ui.home.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.SaurceMyCarRepository
import com.a.luxurycar.code_files.view_model.SaurceMyCarViewModel
import com.a.luxurycar.common.helper.AdapterSpinner
import com.a.luxurycar.common.helper.NetworkHelper
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.databinding.FragmentSaurceMyCarBinding
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class SaurceMyCarFragment :
    BaseFragment<SaurceMyCarViewModel, FragmentSaurceMyCarBinding, SaurceMyCarRepository>() {

    private var firstName = ""
    private var lastName = ""
    private var companyName = ""
    private var email = ""
    private var phone = ""
    private var subject = ""
    private var description = ""
    private var minPrice = ""
    private var maxPrice = ""
    private var makeId = ""
    private var makeName = ""
    private var carModelId = ""
    private var carModelName = ""
    private var yearName = ""

    lateinit var arrMakeListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrCarModelListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrYearListHashMap: ArrayList<HashMap<String, String>>

    override fun getViewModel() = SaurceMyCarViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentSaurceMyCarBinding.inflate(inflater, container, false)

    override fun getRepository() =
        SaurceMyCarRepository(ApiAdapter.buildApi(ApiService::class.java))

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageClickListener()
        getPreviousYearList()
        callMakeListApi()

    }

    private fun callMakeListApi() {
        makeListApi()


        viewModel.makeListResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            arrMakeListHashMap = ArrayList()
            val hashMapDefaultitem = HashMap<String, String>()
            hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
            hashMapDefaultitem.put(Const.KEY_NAME, "Select")
            arrMakeListHashMap.add(hashMapDefaultitem)

            binding.progressBar.visible(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {
                    if (it.values.status == 1) {
                        binding.progressBar.visible(isHidden)
                        if (it.values != null) {
                            for (item in it.values.data) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name.toString())
                                arrMakeListHashMap.add(hashMap)
                            }
                        }
                    } else {
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_LONG)
                            .show()
                    }


                    val adapter = AdapterSpinner(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        arrMakeListHashMap
                    )

                    adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)

                    binding.spinnerSelectMake.setAdapter(adapter)


                }
                is Resource.Failure -> handleApiErrors(it)
                else -> {}
            }
        })
    }

    private fun makeListApi() {
        if (NetworkHelper.isNetworkAvaialble(requireContext())) {
            viewModel.getMakeListResponse()
        } else {

            requireContext().toast("No Internet Connection")
        }
    }

    private fun callCardModelApi() {
        cardModelApi()
        viewModel.carModelResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status == 1) {
                        binding.progressBar.visible(isHidden)
                        if (it.values != null) {
                            arrCarModelListHashMap = ArrayList()

                            val hashMapDefaultitem = HashMap<String, String>()
                            hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
                            hashMapDefaultitem.put(Const.KEY_NAME, "Select")
                            arrCarModelListHashMap.add(hashMapDefaultitem)

                            for (item in it.values.data) {
                                val hashMap = HashMap<String, String>()
                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name)
                                arrCarModelListHashMap.add(hashMap)
                            }
                        }
                        val adapter = AdapterSpinner(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            arrCarModelListHashMap
                        )

                        adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)

                        binding.spinnerSelectModel.setAdapter(adapter)

                    }

                }
                is Resource.Failure -> handleApiErrors(it)
                else -> {}
            }

        })
    }

    private fun cardModelApi() {
        if (NetworkHelper.isNetworkAvaialble(requireContext())) {
            viewModel.getCarModelResponse(makeId)
        } else {

            requireContext().toast("No Internet Connection")
        }
    }

    private fun getPreviousYearList() {
        val list = arrayListOf<String>()
        arrYearListHashMap = ArrayList()

        //default item for all list
        val hashMapDefaultitem = HashMap<String, String>()
        hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
        hashMapDefaultitem.put(Const.KEY_NAME, "Select")

        //add default item
        arrYearListHashMap.add(hashMapDefaultitem)


        val prevYear = Calendar.getInstance()
        list.add(prevYear[Calendar.YEAR].toString())
        var i = 1
        for (i in i until 50) {
            prevYear.add(Calendar.YEAR, -1)
            list.add(prevYear[Calendar.YEAR].toString())
            i.dec()
        }
        for (item in list) {
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "")
            hashMap.put(Const.KEY_NAME, item)
            arrYearListHashMap.add(hashMap)
        }

        val adapter = AdapterSpinner(requireContext(),
            android.R.layout.simple_spinner_item,
            arrYearListHashMap)

        adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        binding.spinnerSelectYear.setAdapter(adapter)
    }

    var countryCode = "+971"

    @SuppressLint("ClickableViewAccessibility")
    private fun manageClickListener() {

        binding.consLayoutCityPhoneCode.setOnClickListener {
            countryCode = binding.ccpCountryCodePicker.selectedCountryCode
        }
        binding.btnSubmit.setOnClickListener {
            if (isValidation()) {
                //  Toast.makeText(requireContext(), "All Field are correct", Toast.LENGTH_LONG).show()
                callSaurceMyCarApi()

            }
        }


        binding.edtTextEnterYourMessage.setOnTouchListener { view, event ->
            view.parent.requestDisallowInterceptTouchEvent(true)
            if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                view.parent.requestDisallowInterceptTouchEvent(false)
            }
            return@setOnTouchListener false
        }

        binding.spinnerSelectMake.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    makeId = arrMakeListHashMap[position].get(Const.KEY_ID).toString()
                    makeName = arrMakeListHashMap[position].get(Const.KEY_NAME).toString()
                    if (makeId != "") {
                        callCardModelApi()
                    }

                }

            }

        binding.spinnerSelectModel.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    carModelId = arrCarModelListHashMap[position].get(Const.KEY_ID).toString()
                    carModelName = arrCarModelListHashMap[position].get(Const.KEY_NAME).toString()

                }

            }

        binding.spinnerSelectYear.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    yearName = arrYearListHashMap[position].get(Const.KEY_NAME).toString()

                }

            }

    }

    private fun callSaurceMyCarApi() {
        saurceMyCarApi()

        viewModel.saurceMyCarResponse.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                binding.progressBar.visible(it is Resource.Loading)
                when (it) {
                    is Resource.Success -> {
                        if (it.values.status == 1) {
                            binding.progressBar.visible(isHidden)
                            if (it.values != null) {
                                clearEditTextData()
                            }
                        }
                    }
                    is Resource.Failure -> handleApiErrors(it)
                    else -> {}
                }

            })
    }

    private fun clearEditTextData() {
        binding.edtTextFirstName.text.clear()
        binding.edtTextLastName.text.clear()
        binding.edtTextCompanyName.text.clear()
        binding.edtTextEnterEmailAddress.text.clear()
        binding.edtTextPhoneNumber.text.clear()
        binding.edtTextSubjet.text?.clear()
        binding.edtTextEnterYourMessage.text?.clear()
        binding.edtTextMinPrice.text?.clear()
        binding.edtTextMaxPrice.text?.clear()
        binding.spinnerSelectMake.setSelection(0)
        binding.spinnerSelectModel.setSelection(0)
        binding.spinnerSelectYear.setSelection(0)
    }

    private fun saurceMyCarApi() {
        val jsonObject = JSONObject()
        try {
            jsonObject.put(Const.PARAM_FIRST_NAME, firstName)
            jsonObject.put(Const.PARAM_LAST_NAME, lastName)
            jsonObject.put(Const.PARAM_EMAIL, email)
            jsonObject.put(Const.PARAM_Phone, /*countryCode.replace("+", "") + "" + */phone)
            jsonObject.put(Const.PARAM_MESSAGE, description)
            jsonObject.put(Const.PARAM_SUBJECT, subject)
            jsonObject.put(Const.PARAM_MIN_PRICE, minPrice)
            jsonObject.put(Const.PARAM_MAX_PRICE, maxPrice)
            jsonObject.put(Const.PARAM_MAKE_ID, makeId)
            jsonObject.put(Const.PARAM_YEAR, yearName)
            jsonObject.put(Const.PARAM_CAR_MODEL_ID, carModelId)


        } catch (e: Exception) {
            e.printStackTrace()
        }

        val body = jsonObject.convertJsonToRequestBody()

        if (NetworkHelper.isNetworkAvaialble(requireContext())) {
            viewModel.getSaurceMyCarResponse(body)
        } else {
            requireContext().toast("No Internet Connection")
        }
    }

    private fun isValidation(): Boolean {
        getDataFromEditField()
        if (Utils.isEmptyOrNull(firstName)) {
            binding.edtTextFirstName.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_first_name))
            return false
        } else if (Utils.isEmptyOrNull(lastName)) {
            binding.edtTextLastName.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_last_name))
            return false
        } else if (Utils.isEmptyOrNull(companyName)) {
            binding.edtTextCompanyName.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_company_name))
            return false
        } else if (Utils.isEmptyOrNull(email)) {
            binding.edtTextEnterEmailAddress.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_email))
            return false
        } else if (!Utils.isValidEmail(email)) {
            binding.edtTextEnterEmailAddress.showErrorAndSetFocus(getStringFromResource(R.string.error_invalid_email))
            return false
        } else if (Utils.isEmptyOrNull(phone)) {
            binding.edtTextPhoneNumber.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_phone_no))
            return false
        } else if (Utils.isEmptyOrNull(minPrice)) {
            binding.edtTextMinPrice.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_enter_price))
            return false
        } else if (Utils.isEmptyOrNull(maxPrice)) {
            binding.edtTextMaxPrice.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_enter_price))
            return false
        } else if (makeName.equals("Select", true)) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_make),
                Toast.LENGTH_LONG).show()
            return false
        } else if (carModelName.equals("Select", true)) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_car_model),
                Toast.LENGTH_LONG).show()
            return false
        } else if (yearName.equals("Select", true)) {
            Toast.makeText(requireContext(),
                getStringFromResource(R.string.error_empty_year),
                Toast.LENGTH_LONG).show()
            return false
        } else if (Utils.isEmptyOrNull(subject)) {
            binding.edtTextSubjet.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_enter_subject))
            return false
        } else if (Utils.isEmptyOrNull(description)) {
            binding.edtTextEnterYourMessage.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_enter_your_message))
            return false
        }

        return true

    }

    private fun getDataFromEditField() {
        firstName = binding.edtTextFirstName.getTextInString()
        lastName = binding.edtTextLastName.getTextInString()
        email = binding.edtTextEnterEmailAddress.getTextInString()
        companyName = binding.edtTextCompanyName.getTextInString()
        phone = binding.edtTextPhoneNumber.getTextInString()
        minPrice = binding.edtTextMinPrice.getTextInString()
        maxPrice = binding.edtTextMaxPrice.getTextInString()
        subject = binding.edtTextSubjet.getTextInString()
        description = binding.edtTextEnterYourMessage.getTextInString()
    }


}