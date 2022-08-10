package com.a.luxurycar.code_files.ui.seller_deshboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.seller_deshboard.model.seller_car_list.Data

class ForRentAdapter(forRentList: ArrayList<Data>) : RecyclerView.Adapter<ForRentAdapter.ViewiewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewiewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item, parent, false);
        return ViewiewHolder(view);


    }

    override fun onBindViewHolder(holder: ViewiewHolder, position: Int) {

        // val item = list[position]
        // Picasso.get().load(item.image).into(holder.imgViewItem);
    }

    override fun getItemCount(): Int {
        return 5
    }


    inner class ViewiewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }
}