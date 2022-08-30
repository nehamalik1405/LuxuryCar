package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.NewEnquiryFormRepository
import com.a.luxurycar.code_files.view_model.NewEnquiryFormViewModel
import com.a.luxurycar.common.helper.NetworkHelper
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.databinding.FragmentNewEnquiryFormStorageBinding
import org.json.JSONObject


class NewEnquiryFormStorageFragment :
    BaseFragment<NewEnquiryFormViewModel, FragmentNewEnquiryFormStorageBinding, NewEnquiryFormRepository>() {

    private var firstName = ""
    private var lastName = ""
    private var companyName = ""
    private var emailAdress = ""
    private var phoneNumber = ""
    private var subject = ""
    private var message = ""

    override fun getViewModel() = NewEnquiryFormViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentNewEnquiryFormStorageBinding.inflate(inflater, container, false)

    override fun getRepository() =
        NewEnquiryFormRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageCLickListners()

    }

    private fun manageCLickListners() {
        binding.btnSubmit.setOnClickListener {
            if (isValidate()) {
                callInspectingEnquiryApi()
            }
        }
    }

    private fun callInspectingEnquiryApi() {
        inspectingEnqueryApi()
        viewModel.inspectingEnquiryResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        requireContext().toast(it.values.message)
                        findNavController().popBackStack()
                        dataClearEditText()
                    } else {
                        requireContext().toast(it.values.message)
                    }
                }
                is Resource.Failure -> handleApiErrors(it)
                else -> {}
            }
        })
    }

    private fun dataClearEditText() {
        binding.edtTextFirstName.text.clear()
        binding.edtTextLastName.text.clear()
        binding.edtTextCompanyName.text.clear()
        binding.edtTextEnterEmailAddress.text.clear()
        binding.edtTextPhoneNumber.text.clear()
        binding.edtTextSubjet.text?.clear()
        binding.edtTextEnterYourMessage.text?.clear()
    }

    private fun inspectingEnqueryApi() {
        val jsonObject = JSONObject()
        try {
            jsonObject.put(Const.PARAM_FIRST_NAME, firstName)
            jsonObject.put(Const.PARAM_LAST_NAME, lastName)
            jsonObject.put(Const.PARAM_EMAIL, emailAdress)
            jsonObject.put(Const.PARAM_Phone, phoneNumber)
            jsonObject.put(Const.PARAM_MESSAGE, message)
            jsonObject.put(Const.PARAM_SUBJECT, subject)
            jsonObject.put(Const.SELLER_COMPANY_NAME, companyName)


        } catch (e: Exception) {
            e.printStackTrace()
        }

        val body = jsonObject.convertJsonToRequestBody()

        if (NetworkHelper.isNetworkAvaialble(requireContext())) {
            viewModel.getInspectingResponse(body)
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