package com.a.luxurycar.code_files.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R

class SourceListAdapter():RecyclerView.Adapter<SourceListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sourcing_list, parent, false);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    // bghfghgfg
    }

    override fun getItemCount(): Int {
        return 5
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

    }
}