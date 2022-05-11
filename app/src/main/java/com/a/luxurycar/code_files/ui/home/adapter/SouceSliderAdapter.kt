package com.a.luxurycar.code_files.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import kotlinx.android.synthetic.main.item_sourcing_slider.view.*

class SouceSliderAdapter():RecyclerView.Adapter<SouceSliderAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sourcing_slider, parent, false);
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    //holder.txtViewContectUs
    }

    override fun getItemCount(): Int {
       return 5
    }
    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
     val txtViewContectUs = itemView.txtViewContectUs
    }
}