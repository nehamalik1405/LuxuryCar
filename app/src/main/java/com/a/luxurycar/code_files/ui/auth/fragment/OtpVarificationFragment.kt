package com.a.luxurycar.code_files.ui.auth.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.common.utils.*
import com.a.luxurycar.databinding.FragmentOtpVarificationBinding


class OtpVarificationFragment : Fragment() {

    var _binding: FragmentOtpVarificationBinding? = null
    val binding get() = _binding!!

    var edtTextCodeOne = ""
    var edtTextCodeTwo = ""
    var edtTextCodeThree = ""
    var edtTextCodeFour = ""
    var otpForVerify = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentOtpVarificationBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.edtTextCodeOne.addTextChangedListener(GenericTextWatcher( binding.edtTextCodeOne, binding.edtTextCodeTwo))
        binding.edtTextCodeTwo.addTextChangedListener(GenericTextWatcher(binding.edtTextCodeTwo, binding.edtTextCodeThree))
        binding.edtTextCodeThree.addTextChangedListener(GenericTextWatcher(binding.edtTextCodeThree,  binding.edtTextCodeFour))
        binding.edtTextCodeFour.addTextChangedListener(GenericTextWatcher( binding.edtTextCodeFour, null))


        binding.edtTextCodeOne.setOnKeyListener(GenericKeyEvent(binding.edtTextCodeOne, null))
        binding.edtTextCodeTwo.setOnKeyListener(GenericKeyEvent(binding.edtTextCodeTwo, binding.edtTextCodeOne))
        binding.edtTextCodeThree.setOnKeyListener(GenericKeyEvent( binding.edtTextCodeThree, binding.edtTextCodeTwo))
        binding.edtTextCodeFour.setOnKeyListener(GenericKeyEvent( binding.edtTextCodeFour,binding.edtTextCodeThree))

        binding.btnVerify.setOnClickListener {
           /* if(isValidId()){
                findNavController().navigate(R.id.updatePasswordFragment)
            }*/
            isValidId()

        }
    }
    private fun isValidId(): Boolean {
        edtTextCodeOne = binding.edtTextCodeOne.text.toString().trim()
        edtTextCodeTwo = binding.edtTextCodeTwo.text.toString().trim()
        edtTextCodeThree = binding.edtTextCodeThree.text.toString().trim()
        edtTextCodeFour = binding.edtTextCodeFour.text.toString().trim()
        otpForVerify = edtTextCodeOne + edtTextCodeTwo + edtTextCodeThree + edtTextCodeFour

        if(!Utils.isEmptyOrNull(otpForVerify)) {
            findNavController().navigate(R.id.updatePasswordFragment)
        }else{
            Toast.makeText(requireContext(), "Please enter OTP", Toast.LENGTH_LONG).show()
        }

        return true
    }

}