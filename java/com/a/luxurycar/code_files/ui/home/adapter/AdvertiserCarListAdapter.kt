package com.a.luxurycar.code_files.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import kotlinx.android.synthetic.main.item_car.view.*

class AdvertiserCarListAdapter: RecyclerView.Adapter<AdvertiserCarListAdapter.ViewiewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewiewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
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

        val recyclerviewSuggestedList = itemView.imgViewItemCar
    }
}