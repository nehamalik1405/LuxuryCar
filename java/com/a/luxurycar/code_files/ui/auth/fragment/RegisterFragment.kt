package com.a.luxurycar.code_files.ui.auth.fragment

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.base.BaseFragment
import com.a.luxurycar.code_files.repository.RegistrationRepository
import com.a.luxurycar.code_files.view_model.RegistrationViewModel
import com.a.luxurycar.common.requestresponse.ApiAdapter
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.utils.Utils
import com.a.luxurycar.common.utils.Utils.Companion.isValidEmail
import com.a.luxurycar.common.utils.getStringFromResource
import com.a.luxurycar.common.utils.showErrorAndSetFocus
import com.a.luxurycar.databinding.FragmentRegisterBinding


class RegisterFragment : BaseFragment<RegistrationViewModel,FragmentRegisterBinding,RegistrationRepository>() {
    var isShowPassword = false
    var name=""
    var email= ""
    var mobileNo=""
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
            if (isRegisterDataValid()){
                Toast.makeText(requireContext(),"Registration Successfully",Toast.LENGTH_LONG).show()
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
                binding.edtTextConfirmPassword.setSelection(binding.edtTextPassword.length())
            }

        }


    }
    private fun getDataFromEditField() {
        name = binding.edtTextName.text.toString().trim()
        email = binding.edtTextEmail.text.toString().trim()
        mobileNo = binding.edtTextMobileNo.text.toString().trim()
        password = binding.edtTextPassword.text.toString().trim()
        confirm_password = binding.edtTextConfirmPassword.text.toString().trim()
    }
    private fun isRegisterDataValid(): Boolean {

        getDataFromEditField()
        if (Utils.isEmptyOrNull(name)) {
            binding.edtTextName.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_name))
            return false
        }  else if (Utils.isEmptyOrNull(email)) {
            binding.edtTextEmail.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_email))
            return false
        } else if (!isValidEmail(email)) {
            binding.edtTextEmail.showErrorAndSetFocus(getStringFromResource(R.string.error_invalid_email))
            return false
        }
        else if (Utils.isEmptyOrNull(mobileNo)) {
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