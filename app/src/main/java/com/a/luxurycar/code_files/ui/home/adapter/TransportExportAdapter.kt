package com.a.luxurycar.code_files.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import kotlinx.android.synthetic.main.item_transport_export.view.*

class TransportExportAdapter():RecyclerView.Adapter<TransportExportAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transport_export, parent, false);
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      // holder.txtViewStep =
    }

    override fun getItemCount(): Int {
       return 5
    }
    inner class ViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){

        val txtViewStep = itemView.txtViewStep

    }
}