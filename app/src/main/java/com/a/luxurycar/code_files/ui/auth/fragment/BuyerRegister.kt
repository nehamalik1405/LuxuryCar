package com.a.luxurycar.code_files.ui.auth.fragment

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.RegistrationRepository
import com.a.luxurycar.code_files.ui.auth.AuthActivity
import com.a.luxurycar.code_files.ui.home.HomeActivity
import com.a.luxurycar.code_files.view_model.RegistrationViewModel
import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.requestresponse.Const
import com.a.luxurycar.common.requestresponse.Resource
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.common.utils.Utils.Companion.getDeviceId
import com.a.luxurycar.common.utils.Utils.Companion.isValidEmail
import com.a.luxurycar.databinding.FragmentRegisterBinding
import org.json.JSONObject


class BuyerRegister : BaseFragment<RegistrationViewModel, FragmentRegisterBinding,RegistrationRepository>() {
    var isShowPassword = false
    var firstName=""
    var lastName=""
    var email= ""
    var phone=""
    var password=""
    var confirm_password=""
    override fun getViewModel() = RegistrationViewModel::class.java


    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    )= FragmentRegisterBinding.inflate(inflater,container,false)

    override fun getRepository()= RegistrationRepository(ApiAdapter.buildApi(ApiService::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageListeners()

    }

    private fun manageListeners() {

        binding.btnRegister.setOnClickListener {
            it.hideKeyboard()
            if (isRegisterDataValid()){
                callRegisterApi()
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

    private fun callRegisterApi() {
        createRequestAndCallRegisterApi()

        viewModel.RegisterResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBarLoginPage.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    if (it.values.status != null && it.values.status == 1) {

                        findNavController().popBackStack()
                    }
                    Toast.makeText(requireContext(),it.values.message,Toast.LENGTH_LONG).show()
                }
                is Resource.Failure -> handleApiErrors(it)
            }
        })
    }

    private fun createRequestAndCallRegisterApi() {
        val jsonObject = JSONObject()
        try {
            jsonObject.put(Const.PARAM_FIRST_NAME, firstName)
            jsonObject.put(Const.PARAM_LAST_NAME, lastName)
            jsonObject.put(Const.PARAM_EMAIL, email)
            jsonObject.put(Const.PARAM_PASSWARD, password)
            jsonObject.put(Const.PARAM_CONFIRM_PASSWARD, confirm_password)
            jsonObject.put(Const.PARAM_Phone, phone)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val body = jsonObject.convertJsonToRequestBody()
        viewModel.getRegisterResponse(body)
    }

    private fun getDataFromEditField() {
        firstName = binding.edtTextFirstName.text.toString().trim()
        lastName = binding.edtTextLastName.text.toString().trim()
        email = binding.edtTextEmail.text.toString().trim()
        phone = binding.edtTextMobileNo.text.toString().trim()
        password = binding.edtTextPassword.text.toString().trim()
        confirm_password = binding.edtTextConfirmPassword.text.toString().trim()
    }
    private fun isRegisterDataValid(): Boolean {

        getDataFromEditField()
        if (Utils.isEmptyOrNull(firstName)) {
            binding.edtTextFirstName.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_first_name))
            return false
        }
        else if (Utils.isEmptyOrNull(lastName)) {
            binding.edtTextLastName.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_last_name))
            return false
        }
        else if (Utils.isEmptyOrNull(email)) {
            binding.edtTextEmail.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_email))
            return false
        } else if (!isValidEmail(email)) {
            binding.edtTextEmail.showErrorAndSetFocus(getStringFromResource(R.string.error_invalid_email))
            return false
        }
        else if (Utils.isEmptyOrNull(phone)) {
            binding.edtTextMobileNo.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_mobile_no))
            return false
        }
        else if (Utils.isEmptyOrNull(password)) {
            binding.edtTextPassword.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_password))
            return false
        } else if (Utils.isEmptyOrNull(confirm_password)) {
            binding.edtTextConfirmPassword.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_confirm_password))
            return false
        } else if (!confirm_password.equals(password)) {
            binding.edtTextConfirmPassword.showErrorAndSetFocus(getStringFromResource(R.string.error_password_not_match))
            return false
        }
        return true
    }

}