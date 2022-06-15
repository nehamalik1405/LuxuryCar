package com.a.luxurycar.code_files.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.a.luxurycar.code_files.ui.home.fragment.BuyerPaymentHistoryFragment
import com.a.luxurycar.code_files.ui.home.fragment.BuyerProfileDetailFragment
import com.a.luxurycar.code_files.ui.home.fragment.BuyerSavedCarsFragment
import com.a.luxurycar.code_files.ui.seller_deshboard.fragment.MyProfileDetailFragment
import com.a.luxurycar.code_files.ui.seller_deshboard.fragment.SellerPaymentHistoryFragment
import com.a.luxurycar.code_files.ui.seller_deshboard.fragment.SellerSavedCarsFragment

class BuyerViewpagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment)  {
    companion object {
        private var onTabChangeListener: OnTabChangeListener? = null
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            onTabChangeListener!!.onTabChange(true)
            return BuyerProfileDetailFragment()
        } else if(position == 1) {
            onTabChangeListener!!.onTabChange(false)
            return BuyerSavedCarsFragment()
        }
        else{
            onTabChangeListener!!.onTabChange(false)
            return BuyerPaymentHistoryFragment()
        }
    }

    interface OnTabChangeListener {
        fun onTabChange(BuyerProfileDetailFragment: Boolean)
    }

    fun setOnTabChangeListener(onTabChangeListenerValue: OnTabChangeListener) {
        onTabChangeListener = onTabChangeListenerValue
    }


}