package com.a.luxurycar.code_files.ui.home.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.ContactRepository
import com.a.luxurycar.code_files.ui.auth.model.LoginCommonResponse
import com.a.luxurycar.code_files.ui.home.HomeActivity
import com.a.luxurycar.code_files.ui.seller_deshboard.SellerDeshboardActivity
import com.a.luxurycar.code_files.view_model.ContactViewModel
import com.a.luxurycar.common.helper.AdapterSpinner
import com.a.luxurycar.common.helper.NetworkHelper
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.databinding.FragmentContactBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONObject

class ContactFragment : BaseFragment<ContactViewModel, FragmentContactBinding, ContactRepository>(),
    OnMapReadyCallback {

    private lateinit var map: GoogleMap
    var firstName = ""
    var lastName = ""
    var email = ""
    var phone = ""
    var description = ""
    lateinit var arrCountryCodeListHashMap: ArrayList<HashMap<String, String>>

    override fun getViewModel() = ContactViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentContactBinding.inflate(inflater, container, false)

    override fun getRepository() = ContactRepository((ApiAdapter.buildApi(ApiService::class.java)))
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        arrCountryCodeListHashMap = arrayListOf()
        //setCountrySpinner()
        manageClickListener()
    }

    /*private fun setCountrySpinner() {
        val list = arrayListOf<String>("+917", "+91", "+1")

        for (item in list) {
            val hashMap = HashMap<String, String>()
            hashMap.put(Const.KEY_ID, "")
            hashMap.put(Const.KEY_NAME, item)
            arrCountryCodeListHashMap.add(hashMap)
        }


        val adapterCountryCode = AdapterSpinner(requireContext(),
            android.R.layout.simple_spinner_item,
            arrCountryCodeListHashMap,
            "contectFragment")


        //Drop down layout style - list view for body type
        adapterCountryCode.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        binding.spinnerCountryCode.setAdapter(adapterCountryCode)
    }*/

    @SuppressLint("ClickableViewAccessibility")
    private fun manageClickListener() {
        binding.btnSend.setOnClickListener {
            if (isValidate()) {
                callContactUsApi()
                //  Toast.makeText(requireContext(), "All Field are correct", Toast.LENGTH_LONG).show()
            }
        }

        binding.edtTextEnterYourMessage.setOnTouchListener { view, event ->
            view.parent.requestDisallowInterceptTouchEvent(true)
            if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                view.parent.requestDisallowInterceptTouchEvent(false)
            }
            return@setOnTouchListener false
        }
    }

    private fun callContactUsApi() {
        contactUsApi()
        viewModel.contactusResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        requireContext().toast(it.values.message)
                        dataClearEditText()
                    } else {
                        requireContext().toast(it.values.message)
                    }
                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })


    }

    private fun contactUsApi() {
        val jsonObject = JSONObject()
        try {
            jsonObject.put(Const.PARAM_FIRST_NAME, firstName)
            jsonObject.put(Const.PARAM_LAST_NAME, lastName)
            jsonObject.put(Const.PARAM_EMAIL, email)
            jsonObject.put(Const.PARAM_Phone, phone)
            jsonObject.put(Const.PARAM_MESSAGE, description)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val body = jsonObject.convertJsonToRequestBody()


        if (NetworkHelper.isNetworkAvaialble(requireContext())) {
            viewModel.getContactUsResponse(body)
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
        } else if (Utils.isEmptyOrNull(email)) {
            binding.edtTextEnterEmail.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_email))
            return false
        } else if (!Utils.isValidEmail(email)) {
            binding.edtTextEnterEmail.showErrorAndSetFocus(getStringFromResource(R.string.error_invalid_email))
            return false
        } else if (Utils.isEmptyOrNull(phone)) {
            binding.edtTextPhoneNumber.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_phone_no))
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
        email = binding.edtTextEnterEmail.getTextInString()
        phone = binding.edtTextPhoneNumber.getTextInString()
        description = binding.edtTextEnterYourMessage.getTextInString()

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val india = LatLng(23.63936, 79.14712)
        map.addMarker(MarkerOptions().position(india).title("India location"))
        map.moveCamera(CameraUpdateFactory.newLatLng(india))
    }

    fun dataClearEditText(){
        binding.edtTextFirstName.text.clear()
        binding.edtTextLastName.text.clear()
        binding.edtTextEnterEmail.text.clear()
        binding.edtTextPhoneNumber.text.clear()
        binding.edtTextEnterYourMessage.text?.clear()

    }

}