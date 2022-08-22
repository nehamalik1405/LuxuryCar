package com.a.luxurycar.code_files.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.model.product_detail_response.Detail
import kotlinx.android.synthetic.main.item_details.view.*

class ProductDetailOfCarAdapter(val arrCarDetailList: ArrayList<Detail>) :
    RecyclerView.Adapter<ProductDetailOfCarAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_details, parent, false);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = arrCarDetailList[position]
        holder.txtViewMake.text  = data.title
        holder.txtViewMakeResult.text = data.name
    }

    override fun getItemCount(): Int {
        return arrCarDetailList.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtViewMake = itemView.txtViewMake
        val txtViewMakeResult = itemView.txtViewMakeResult
    }
}