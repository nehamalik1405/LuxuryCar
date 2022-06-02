package com.a.luxurycar.code_files.ui.auth.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.a.luxurycar.R
import com.a.luxurycar.databinding.FragmentRegisterBuyerAndSellerBinding


class RegisterBuyerAndSellerFragment : Fragment() {
    var _binding: FragmentRegisterBuyerAndSellerBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRegisterBuyerAndSellerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        manageClickListeners()
    }

    private fun manageClickListeners() {

        binding.btnBuyer.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }

        binding.btnSeller.setOnClickListener {
            findNavController().navigate(R.id.sellerRegisterFragment)
        }

    }

}