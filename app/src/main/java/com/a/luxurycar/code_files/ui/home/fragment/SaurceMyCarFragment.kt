package com.a.luxurycar.code_files.ui.home.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.a.luxurycar.R
import com.a.luxurycar.common.utils.Utils
import com.a.luxurycar.common.utils.getStringFromResource
import com.a.luxurycar.common.utils.getTextInString
import com.a.luxurycar.common.utils.showErrorAndSetFocus
import com.a.luxurycar.databinding.FragmentSaurceMyCarBinding

class SaurceMyCarFragment : Fragment() {

    var _binding:FragmentSaurceMyCarBinding?=null
    val binding get() = _binding!!
    var firstName = ""
    var lastName = ""
    var companyName = ""
    var email = ""
    var phone = ""
    var subject = ""
    var description = ""
    var minPrice = ""
    var maxPrice = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSaurceMyCarBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSubmit.setOnClickListener {
            if (isValidation()){
                Toast.makeText(requireContext(), "All Field are correct", Toast.LENGTH_LONG).show()
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

    private fun isValidation(): Boolean {
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
        }
        else if (Utils.isEmptyOrNull(email)) {
            binding.edtTextEnterEmailAddress.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_email))
            return false
        }
        else if (!Utils.isValidEmail(email)) {
            binding.edtTextEnterEmailAddress.showErrorAndSetFocus(getStringFromResource(R.string.error_invalid_email))
            return false
        } else if (Utils.isEmptyOrNull(phone)) {
            binding.edtTextPhoneNumber.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_phone_no))
            return false
        }
        else if (Utils.isEmptyOrNull(minPrice)) {
            binding.edtTextMinPrice.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_enter_price))
            return false
        }else if (Utils.isEmptyOrNull(maxPrice)) {
            binding.edtTextMaxPrice.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_enter_price))
            return false
        }
        else if (Utils.isEmptyOrNull(subject)) {
            binding.edtTextSubjet.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_enter_subject))
            return false
        }
        else if (Utils.isEmptyOrNull(description)) {
            binding.edtTextEnterYourMessage.showErrorAndSetFocus(getStringFromResource(R.string.error_empty_enter_your_message))
            return false
        }

        return true

    }

    private fun getDataFromEditField() {
        firstName = binding.edtTextFirstName.getTextInString()
        lastName = binding.edtTextLastName.getTextInString()
        email = binding.edtTextEnterEmailAddress.getTextInString()
        companyName = binding.edtTextCompanyName.getTextInString()
        phone = binding.edtTextPhoneNumber.getTextInString()
        minPrice = binding.edtTextMinPrice.getTextInString()
        maxPrice = binding.edtTextMaxPrice.getTextInString()
        subject = binding.edtTextSubjet.getTextInString()
        description = binding.edtTextEnterYourMessage.getTextInString()
    }

}