package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.BookAnAppointmentRepository
import com.a.luxurycar.code_files.view_model.BookAnAppointmentViewmodel
import com.a.luxurycar.common.helper.AdapterSpinner
import com.a.luxurycar.common.helper.NetworkHelper
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.databinding.FragmentBookAnAppointmentBinding
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class BookAnAppointmentFragment :
    BaseFragment<BookAnAppointmentViewmodel, FragmentBookAnAppointmentBinding, BookAnAppointmentRepository>() {

    private var firstName = ""
    private var lastName = ""
    private var companyName = ""
    private var emailAdress = ""
    private var phoneNumber = ""
    private var subject = ""
    private var message = ""
    private var makeId = ""
    private var makeName = ""
    private var carModelId = ""
    private var carModelName = ""
    private var yearName = ""

    lateinit var arrMakeListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrCarModelListHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrYearListHashMap: ArrayList<HashMap<String, String>>

    override fun getViewModel() = BookAnAppointmentViewmodel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentBookAnAppointmentBinding.inflate(inflater, container, false)

    override fun getRepository() =
        BookAnAppointmentRepository(ApiAdapter.buildApi(ApiService::class.java))


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callMakeListApi()
        getPreviousYearList()
        manageListners()
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

    private fun manageListners() {

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

        binding.btnSubmit.setOnClickListener {
            if (isValidate()) {
                callBookAppointmenetApi()
            }
        }
    }

    private fun callBookAppointmenetApi() {
        bookAppointmentApi()

        viewModel.appointmentEnquiryResponse.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                binding.progressBar.visible(it is Resource.Loading)
                when (it) {
                    is Resource.Success -> {
                        if (it.values.status == 1) {
                            binding.progressBar.visible(isHidden)
                            if (it.values != null) {
                                findNavController().popBackStack()
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
        binding.spinnerSelectMake.setSelection(0)
        binding.spinnerSelectModel.setSelection(0)
        binding.spinnerSelectYear.setSelection(0)
    }

    private fun bookAppointmentApi() {
        val jsonObject = JSONObject()
        try {
            jsonObject.put(Const.PARAM_FIRST_NAME, firstName)
            jsonObject.put(Const.PARAM_LAST_NAME, lastName)
            jsonObject.put(Const.PARAM_EMAIL, emailAdress)
            jsonObject.put(Const.PARAM_Phone, phoneNumber)
            jsonObject.put(Const.PARAM_MESSAGE, message)
            jsonObject.put(Const.PARAM_SUBJECT, subject)
            jsonObject.put(Const.PARAM_MAKE_ID, makeId)
            jsonObject.put(Const.PARAM_YEAR, yearName)
            jsonObject.put(Const.PARAM_CAR_MODEL_ID, carModelId)
            //jsonObject.put(Const.SELLER_COMPANY_NAME, carModelId)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        val body = jsonObject.convertJsonToRequestBody()

        if (NetworkHelper.isNetworkAvaialble(requireContext())) {
            viewModel.getBookApointmentEnquiryResponse(body)
        } else {
            requireContext().toast("No Internet Connection")
        }
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


    private fun isValidate(): Boolean {

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
        } else if (Utils.isEmptyOrNull(emailAdress)) {
            binding.edtTextEnterEmailAddress.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_email))
            return false
        } else if (!Utils.isValidEmail(emailAdress)) {
            binding.edtTextEnterEmailAddress.showErrorAndSetFocus(getStringFromResource(R.string.error_invalid_email))
            return false
        } else if (Utils.isEmptyOrNull(phoneNumber)) {
            binding.edtTextPhoneNumber.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_phone_no))
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
            binding.edtTextSubjet.showErrorAndSetFocus(getStringFromResource(R.string.error_subject_message))
            return false
        } else if (Utils.isEmptyOrNull(message)) {
            binding.edtTextEnterYourMessage.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_enter_your_message))
            return false
        }
        return true
    }


    private fun getDataFromEditField() {
        firstName = binding.edtTextFirstName.getTextInString()
        lastName = binding.edtTextLastName.getTextInString()
        companyName = binding.edtTextCompanyName.getTextInString()
        emailAdress = binding.edtTextEnterEmailAddress.getTextInString()
        phoneNumber = binding.edtTextPhoneNumber.getTextInString()
        subject = binding.edtTextSubjet.getTextInString()
        message = binding.edtTextEnterYourMessage.getTextInString()

    }

}