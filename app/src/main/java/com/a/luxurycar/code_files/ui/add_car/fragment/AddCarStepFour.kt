package com.a.luxurycar.code_files.ui.add_car.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a.luxurycar.databinding.FragmentAddCarStepFourBinding


class AddCarStepFour : Fragment() {

    var _binding: FragmentAddCarStepFourBinding?=null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentAddCarStepFourBinding.inflate(inflater,container,false)
        return binding.root
    }
}