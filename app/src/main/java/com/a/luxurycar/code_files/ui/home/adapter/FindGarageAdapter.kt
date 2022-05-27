package com.a.luxurycar.code_files.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R

class FindGarageAdapter():RecyclerView.Adapter<FindGarageAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_garages, parent, false);
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      //htrhrtshty
    }

    override fun getItemCount(): Int {
       return 10
    }
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}