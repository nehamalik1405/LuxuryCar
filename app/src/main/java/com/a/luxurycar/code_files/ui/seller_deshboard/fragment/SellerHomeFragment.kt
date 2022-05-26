package com.a.luxurycar.code_files.ui.seller_deshboard.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.seller_deshboard.adapter.ForRentAdapter
import com.a.luxurycar.code_files.ui.seller_deshboard.adapter.ForSaleAdapter
import com.a.luxurycar.databinding.FragmentSellerHomeBinding

class SellerHomeFragment : Fragment() {
 var _binding:FragmentSellerHomeBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSellerHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        manageClickListener()
        setForSaleList()
        setForRentList()
    }

    private fun setForRentList() {
      val forSaleAdapter = ForSaleAdapter()
        binding.recyclerViewForSale.adapter = forSaleAdapter
        binding.recyclerViewForSale.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)
    }

    private fun setForSaleList() {
        val forRentAdapter = ForRentAdapter()
        binding.recyclerViewForRent.adapter = forRentAdapter
        binding.recyclerViewForRent.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)
    }

    private fun manageClickListener() {
        binding.consLayoutTabForSale.setOnClickListener {
            binding.consLayoutTabForRent.setBackgroundResource(0)
            binding.consLayoutTabForBuyerEnqukles.setBackgroundResource(0)
            binding.consLayoutTabForSale.setBackgroundResource(R.drawable.drawable_tab_background)
            binding.recyclerViewForSale.visibility = View.VISIBLE
            binding.recyclerViewForRent.visibility = View.GONE

        }
        binding.consLayoutTabForRent.setOnClickListener {
            binding.recyclerViewForSale.visibility = View.GONE
            binding.recyclerViewForRent.visibility = View.VISIBLE
            binding.consLayoutTabForSale.setBackgroundResource(0)
            binding.consLayoutTabForBuyerEnqukles.setBackgroundResource(0)
            binding.consLayoutTabForRent.setBackgroundResource(R.drawable.drawable_tab_background)

        }
        binding.consLayoutTabForBuyerEnqukles.setOnClickListener {
            binding.consLayoutTabForRent.setBackgroundResource(0)
            binding.consLayoutTabForSale.setBackgroundResource(0)
            binding.consLayoutTabForBuyerEnqukles.setBackgroundResource(R.drawable.drawable_tab_background)
        }
    }

}