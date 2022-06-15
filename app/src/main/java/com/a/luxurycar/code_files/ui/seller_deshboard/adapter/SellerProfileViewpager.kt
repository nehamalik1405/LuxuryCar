package com.a.luxurycar.code_files.ui.seller_deshboard.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.a.luxurycar.code_files.ui.seller_deshboard.fragment.MyProfileDetailFragment
import com.a.luxurycar.code_files.ui.seller_deshboard.fragment.SellerPaymentHistoryFragment
import com.a.luxurycar.code_files.ui.seller_deshboard.fragment.SellerSavedCarsFragment

class SellerProfileViewpager(fragment : Fragment) : FragmentStateAdapter(fragment) {

    companion object {
        private var onTabChangeListener: OnTabChangeListener? = null
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            onTabChangeListener!!.onTabChange(false)
            return MyProfileDetailFragment()
        } else if(position == 1) {
            onTabChangeListener!!.onTabChange(true)
            return SellerSavedCarsFragment()
        }
        else{
            onTabChangeListener!!.onTabChange(true)
            return SellerPaymentHistoryFragment()
        }
    }

    interface OnTabChangeListener {
        fun onTabChange(MyProfileDetailFragment: Boolean)
    }

    fun setOnTabChangeListener(onTabChangeListenerValue: OnTabChangeListener) {
        onTabChangeListener = onTabChangeListenerValue
    }

}