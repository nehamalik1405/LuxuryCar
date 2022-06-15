package com.a.luxurycar.code_files.ui.seller_deshboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R

class PaymentHistoryAdapter():RecyclerView.Adapter<PaymentHistoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PaymentHistoryAdapter.ViewHolder {
       val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_history, parent, false)
       return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaymentHistoryAdapter.ViewHolder, position: Int) {
       //
    }

    override fun getItemCount(): Int {
    return 8
    }

   inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


    }
}