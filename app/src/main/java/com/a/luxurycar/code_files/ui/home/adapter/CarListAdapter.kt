package com.a.luxurycar.code_files.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import kotlinx.android.synthetic.main.item_advertiser.view.*

class CarListAdapter(val context: Context): RecyclerView.Adapter<CarListAdapter.ViewiewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewiewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return ViewiewHolder(view);


    }

    override fun onBindViewHolder(holder: ViewiewHolder, position: Int) {


    }

    override fun getItemCount(): Int {
        return 6
    }


    inner class ViewiewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val recyclerviewSuggestedList = itemView.recyclerviewSuggestedList
    }
}