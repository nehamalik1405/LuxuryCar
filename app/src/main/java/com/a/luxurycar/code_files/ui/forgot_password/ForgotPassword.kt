package com.a.luxurycar.code_files.ui.forgot_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.HomeActivity
import com.a.luxurycar.common.utils.StartActivity
import com.a.luxurycar.common.utils.Utils
import com.a.luxurycar.common.utils.getStringFromResource
import com.a.luxurycar.common.utils.showErrorAndSetFocus
import com.a.luxurycar.databinding.FragmentForgotPasswordBinding



class ForgotPassword : Fragment() {
    var _binding: FragmentForgotPasswordBinding? = null
    val binding get() = _binding!!

    var email = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageOnClickListener()
    }

    private fun manageOnClickListener() {
        binding.btnSubmit.setOnClickListener {
            if (isValidId()){
               findNavController().navigate(R.id.loginFragment)
            }
        }
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