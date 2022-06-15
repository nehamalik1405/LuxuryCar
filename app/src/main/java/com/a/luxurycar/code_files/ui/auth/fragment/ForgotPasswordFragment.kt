package com.a.luxurycar.code_files.ui.auth.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.ForgotPasswordRepository
import com.a.luxurycar.code_files.view_model.ForgotPasswordViewModel
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.databinding.FragmentForgotPasswordBinding
import org.json.JSONObject


class ForgotPasswordFragment :
    BaseFragment<ForgotPasswordViewModel, FragmentForgotPasswordBinding, ForgotPasswordRepository>() {

    var email = ""

    override fun getViewModel() = ForgotPasswordViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ) = FragmentForgotPasswordBinding.inflate(inflater, container, false)

    override fun getRepository() =
        ForgotPasswordRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageOnClickListener()

    }

    private fun liveDataObserver() {
        viewModel.sendOtpResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarForgotPasswordPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {
                        val bundle = Bundle()
                        bundle.putString("email", email)
                        findNavController().navigate(R.id.otpVarificationFragment,bundle)
                    }
                    if (it.values != null && !it.values.message.isNullOrEmpty()) {
                        Toast.makeText(requireContext(), it.values.message, Toast.LENGTH_SHORT)
                            .show()
                    }

                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })
    }

    private fun manageOnClickListener() {
        binding.btnSubmit.setOnClickListener {
            it.hideKeyboard()
            if (isValidId()) {
                callSendOtpApi()
            }
        }
    }

    private fun callSendOtpApi() {
        liveDataObserver()
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
        } else if (!Utils.isValidEmail(email)) {
            binding.edtTextEmail.showErrorAndSetFocus(getStringFromResource(R.string.error_invalid_email))
            return false
        }
        return true
    }


}