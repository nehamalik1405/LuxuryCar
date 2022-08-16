package com.a.luxurycar.code_files.ui.seller_deshboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.seller_deshboard.model.seller_car_list.Data
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_car.view.*

class ForSaleAdapter(val forSaleList: ArrayList<Data>) :RecyclerView.Adapter<ForSaleAdapter.ViewiewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewiewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item, parent, false);
        return ViewiewHolder(view);


    }

    override fun onBindViewHolder(holder: ViewiewHolder, position: Int) {

       // val item = forSaleList[position]

    }

    override fun getItemCount(): Int {
        return 5
    }


    inner class ViewiewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }
}