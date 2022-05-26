package com.a.luxurycar.code_files.ui.add_car.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a.luxurycar.databinding.FragmentAddCarStepThreeBinding


class AddCarStepThree : Fragment() {
    var _binding: FragmentAddCarStepThreeBinding?=null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentAddCarStepThreeBinding.inflate(inflater,container,false)
        return binding.root
    }
}