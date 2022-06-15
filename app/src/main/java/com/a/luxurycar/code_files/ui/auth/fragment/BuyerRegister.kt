package com.a.luxurycar.code_files.ui.auth.fragment

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.BuyerRegistrationRepository
import com.a.luxurycar.code_files.view_model.BuyerRegistrationViewModel
import com.a.luxurycar.common.helper.AdapterSpinner
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.common.utils.Utils.Companion.isValidEmail
import com.a.luxurycar.databinding.FragmentRegisterBinding
import org.json.JSONObject


class BuyerRegister :
    BaseFragment<BuyerRegistrationViewModel, FragmentRegisterBinding, BuyerRegistrationRepository>() {
    var isShowPassword = false
    var firstName = ""
    var lastName = ""
    var country = ""
    var state = ""
    var city = ""
    var email = ""
    var phone = ""
    var password = ""
    var confirmPassword = ""
    var country_id = ""
    var stateId = ""
    var cityId = ""
    lateinit var arrListCountryHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrListStateHashMap: ArrayList<HashMap<String, String>>
    lateinit var arrListCityHashMap: ArrayList<HashMap<String, String>>
    override fun getViewModel() = BuyerRegistrationViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentRegisterBinding.inflate(inflater, container, false)

    override fun getRepository() =
        BuyerRegistrationRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callCountryList()
        manageListeners()
        liveDataObserver()
    }

    private fun callCountryList() {
            viewModel.getCountryList()
            viewModel.countryResponse.observe(viewLifecycleOwner, Observer {
                arrListCountryHashMap = ArrayList()
                val hashMapDefaultitem = HashMap<String, String>()
                hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
                hashMapDefaultitem.put(Const.KEY_NAME, "Country Name")
                arrListCountryHashMap.add(hashMapDefaultitem)


                binding.progressBarRegisterPage.visible(it is Resource.Loading)
                when (it) {
                    is Resource.Success -> {

                        if (it.values.status == 1) {
                            binding.progressBarRegisterPage.visible(isHidden)
                            if (it != null) {


                                for (item in it.values.data) {
                                    val hashMap = java.util.HashMap<String, String>()
                                    hashMap.put(Const.KEY_ID, "" + item.id)
                                    hashMap.put(Const.KEY_NAME, item.name.toString())
                                    arrListCountryHashMap.add(hashMap)
                                }
                            }
                        }


                        val adapter = AdapterSpinner(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            arrListCountryHashMap
                        )


                        //Drop down layout style - list view with radio button
                        adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
                        //attaching data adapter to spinner
                        binding.spinnerCountry.setAdapter(adapter)


                    }
                    is Resource.Failure -> handleApiErrors(it)
                }


            })




    }




    private fun liveDataObserver() {
        viewModel.registerResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarRegisterPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        findNavController().navigate(R.id.loginFragment)

                    }

                    if (!Utils.isEmptyOrNull(it.values.message)) {
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_LONG)
                            .show()
                    }

                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })
    }

    private fun manageListeners() {

        binding.btnRegister.setOnClickListener {
            it.hideKeyboard()
            if (isRegisterDataValid()) {
                callRegisterApi()
            }
        }
        binding.spinnerCountry.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    country_id = arrListCountryHashMap[position].get(Const.KEY_ID).toString()
                    callStateListApi()
                }

            }
        binding.spinnerState.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    stateId = arrListStateHashMap[position].get(Const.KEY_ID).toString()
                    callCityListApi()
                }

            }
        binding.spinnerCity.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    cityId = arrListCityHashMap[position].get(Const.KEY_ID).toString()

                }

            }

        binding.txtViewLogin.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        binding.imgViewEyePassword.setOnClickListener {
            isShowPassword = !isShowPassword
            if (isShowPassword) {
                binding.edtTextPassword.transformationMethod = null
                binding.imgViewEyePassword.setImageResource(R.mipmap.ic_show_icon)
                binding.edtTextPassword.setSelection(binding.edtTextPassword.length())
            } else {
                binding.edtTextPassword.transformationMethod = PasswordTransformationMethod()
                binding.imgViewEyePassword.setImageResource(R.mipmap.ic_hide_icon)
                binding.edtTextPassword.setSelection(binding.edtTextPassword.length())
            }
        }

        binding.imgViewEyeConfirmPassword.setOnClickListener {
            isShowPassword = !isShowPassword
            if (isShowPassword) {
                binding.edtTextConfirmPassword.transformationMethod = null
                binding.imgViewEyeConfirmPassword.setImageResource(R.mipmap.ic_show_icon)
                binding.edtTextConfirmPassword.setSelection(binding.edtTextPassword.length())
            } else {
                binding.edtTextConfirmPassword.transformationMethod = PasswordTransformationMethod()
                binding.imgViewEyeConfirmPassword.setImageResource(R.mipmap.ic_hide_icon)
                binding.edtTextConfirmPassword.setSelection(binding.edtTextConfirmPassword.length())
            }

        }


    }

    private fun callCityListApi() {
        viewModel.getCitiesList(stateId)
        viewModel.cityResponse.observe(viewLifecycleOwner, Observer {
            arrListCityHashMap = ArrayList()
            val hashMapDefaultitem = HashMap<String, String>()
            hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
            hashMapDefaultitem.put(Const.KEY_NAME, "City Name")
            arrListCityHashMap.add(hashMapDefaultitem)


            binding.progressBarRegisterPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status == 1) {
                        binding.progressBarRegisterPage.visible(isHidden)

                        if (it != null) {


                            for (item in it.values.data) {
                                val hashMap = java.util.HashMap<String, String>()

                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name.toString())
                                arrListCityHashMap.add(hashMap)
                            }
                        }
                    }

                    val adapter = AdapterSpinner(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        arrListCityHashMap
                    )


                    //Drop down layout style - list view with radio button
                    adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
                    //attaching data adapter to spinner
                    binding.spinnerCity.setAdapter(adapter)


                }
                is Resource.Failure -> handleApiErrors(it)
            }


        })

    }

    private fun callStateListApi() {
        viewModel.getSateList(country_id)
        viewModel.stateResponse.observe(viewLifecycleOwner, Observer {
            arrListStateHashMap = ArrayList()
            val hashMapDefaultitem = HashMap<String, String>()
            hashMapDefaultitem.put(Const.KEY_ID, "" + 0)
            hashMapDefaultitem.put(Const.KEY_NAME, "State Name")
            arrListStateHashMap.add(hashMapDefaultitem)


            binding.progressBarRegisterPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status == 1) {
                        binding.progressBarRegisterPage.visible(isHidden)

                        if (it != null) {


                            for (item in it.values.data) {
                                val hashMap = java.util.HashMap<String, String>()

                                hashMap.put(Const.KEY_ID, "" + item.id)
                                hashMap.put(Const.KEY_NAME, item.name.toString())
                                arrListStateHashMap.add(hashMap)
                            }
                        }
                    }

                    val adapter = AdapterSpinner(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        arrListStateHashMap
                    )


                    //Drop down layout style - list view with radio button
                    adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
                    //attaching data adapter to spinner
                    binding.spinnerState.setAdapter(adapter)


                }
                is Resource.Failure -> handleApiErrors(it)
            }


        })

    }

    private fun callRegisterApi() {
        val jsonObject = JSONObject()
        try {
            jsonObject.put(Const.PARAM_FIRST_NAME, firstName)
            jsonObject.put(Const.PARAM_LAST_NAME, lastName)
            jsonObject.put(Const.PARAM_EMAIL, email)
            jsonObject.put(Const.PARAM_Phone, phone)
            jsonObject.put(Const.PARAM_PASSWARD, password)
            jsonObject.put(Const.PARAM_CONFIRM_PASSWARD, confirmPassword)
            jsonObject.put(Const.PARAM_COUNTRY, country_id.toInt())
            jsonObject.put(Const.PARAM_STATE, stateId.toInt())
            jsonObject.put(Const.PARAM_CITY, cityId.toInt())

        } catch (e: Exception) {
            e.printStackTrace()
        }

        val body = jsonObject.convertJsonToRequestBody()
        viewModel.getRegisterResponse(body)
    }

    private fun getDataFromEditField() {
        firstName = binding.edtTextFirstName.getTextInString()
        lastName = binding.edtTextLastName.getTextInString()
       // country = binding.edtTextCountryName.getTextInString()
     //   state = binding.edtTextStateName.getTextInString()
      //  city = binding.edtTextCityName.getTextInString()
        email = binding.edtTextEmail.getTextInString()
        phone = binding.edtTextPhoneNo.getTextInString()
        password = binding.edtTextPassword.getTextInString()
        confirmPassword = binding.edtTextConfirmPassword.getTextInString()
    }

    private fun isRegisterDataValid(): Boolean {

        getDataFromEditField()
        if (Utils.isEmptyOrNull(firstName)) {
            binding.edtTextFirstName.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_first_name))
            return false
        } else if (Utils.isEmptyOrNull(lastName)) {
            binding.edtTextLastName.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_last_name))
            return false
        }
        else if (country_id == "0") {
            Toast.makeText(requireContext(), getStringFromResource(R.string.error_empty_select_country), Toast.LENGTH_LONG).show()
            return false
        }
        else if (stateId== "0") {
            Toast.makeText(requireContext(), getStringFromResource(R.string.error_empty_select_state), Toast.LENGTH_LONG).show()
            return false
        }
        else if (cityId == "0") {
            Toast.makeText(requireContext(), getStringFromResource(R.string.error_empty_select_city), Toast.LENGTH_LONG).show()
            return false
        } else if (Utils.isEmptyOrNull(email)) {
            binding.edtTextEmail.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_email))
            return false
        } else if (!isValidEmail(email)) {
            binding.edtTextEmail.showErrorAndSetFocus(getStringFromResource(R.string.error_invalid_email))
            return false
        } else if (Utils.isEmptyOrNull(phone)) {
            binding.edtTextPhoneNo.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_mobile_no))
            return false
        }
        else if (phone.length < 10) {
            binding.edtTextPhoneNo.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_valid_number))
            return false
        }
        else if (Utils.isEmptyOrNull(password)) {
            binding.edtTextPassword.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_password))
            return false
        } else if (Utils.isEmptyOrNull(confirmPassword)) {
            binding.edtTextConfirmPassword.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_confirm_password))
            return false
        } else if (!confirmPassword.equals(password)) {
            binding.edtTextConfirmPassword.showErrorAndSetFocus(getStringFromResource(R.string.error_password_not_match))
            return false
        }
        return true
    }

}