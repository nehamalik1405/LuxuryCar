package com.a.luxurycar.code_files.ui.auth.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.ForgotPasswordRepository
import com.a.luxurycar.code_files.view_model.ForgotPasswordViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.utils.Utils
import com.a.luxurycar.common.utils.convertJsonToRequestBody
import com.a.luxurycar.common.utils.getStringFromResource
import com.a.luxurycar.common.utils.showErrorAndSetFocus
import com.a.luxurycar.databinding.FragmentForgotPasswordBinding
import org.json.JSONObject


class ForgotPassword : BaseFragment<ForgotPasswordViewModel,FragmentForgotPasswordBinding,ForgotPasswordRepository>() {

    var email = ""

    override fun getViewModel()=ForgotPasswordViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentForgotPasswordBinding.inflate(inflater,container,false)

    override fun getRepository()= ForgotPasswordRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageOnClickListener()
    }

    private fun manageOnClickListener() {
        binding.btnSubmit.setOnClickListener {
            if (isValidId()){
                callSendOtpApi()
               //findNavController().navigate(R.id.otpVarificationFragment)
            }
        }
    }

    private fun callSendOtpApi() {
       callRequestSendOtpApi()
    }

    private fun callRequestSendOtpApi() {
        val jsonObject = JSONObject()
        try {
            jsonObject.put(Const.PARAM_EMAIL, email)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        val body = jsonObject.convertJsonToRequestBody()
        viewModel.getSendOtpResponse(body)
    }

    private fun isValidId(): Boolean {
        email = binding.edtTextEmail.text.toString().trim()
        if (Utils.isEmptyOrNull(email)) {
            binding.edtTextEmail.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_email))
            return false
        }
        else if (!Utils.isValidEmail(email)) {
            binding.edtTextEmail.showErrorAndSetFocus(getStringFromResource(R.string.error_invalid_email))
            return false
        }
        return true
    }




}