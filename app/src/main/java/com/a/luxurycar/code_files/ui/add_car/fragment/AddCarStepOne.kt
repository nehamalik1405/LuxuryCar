package com.a.luxurycar.code_files.ui.add_car.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a.luxurycar.R
import com.a.luxurycar.databinding.FragmentAddCarStepOneBinding


class AddCarStepOne : Fragment() {
     var _binding:FragmentAddCarStepOneBinding?=null
     val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentAddCarStepOneBinding.inflate(inflater,container,false)
        return binding.root
    }


}