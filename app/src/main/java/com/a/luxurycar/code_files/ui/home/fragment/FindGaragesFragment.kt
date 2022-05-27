package com.a.luxurycar.code_files.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.adapter.FindGarageAdapter
import com.a.luxurycar.databinding.FragmentFindGaragesBinding


class FindGaragesFragment : Fragment() {

    var _binding: FragmentFindGaragesBinding?=null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFindGaragesBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageClickListener()
    }

    private fun manageClickListener() {

        val findGarageAdapter =FindGarageAdapter()
        binding.recyclerViewFindGarages.adapter = findGarageAdapter
        binding.recyclerViewFindGarages.setLayoutManager(GridLayoutManager(requireContext(), 2))

    }


}