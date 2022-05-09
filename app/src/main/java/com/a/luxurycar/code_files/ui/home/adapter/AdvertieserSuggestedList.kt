package com.a.luxurycar.code_files.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import kotlinx.android.synthetic.main.item_advertiser.view.*


class AdvertieserSuggestedList(val context: Context): RecyclerView.Adapter<AdvertieserSuggestedList.ViewiewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewiewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advertiser, parent, false);
        return ViewiewHolder(view);


    }

    override fun onBindViewHolder(holder: ViewiewHolder, position: Int) {

        val advertieserSuggestedList = AdvertiserCarListAdapter()
        holder.recyclerviewSuggestedList.adapter = advertieserSuggestedList
        holder.recyclerviewSuggestedList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)

        holder.imgViewSuggestedRightArrow.setOnClickListener {

        }

    }

    override fun getItemCount(): Int {
        return 3
    }


    inner class ViewiewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val recyclerviewSuggestedList = itemView.recyclerviewSuggestedList
        val imgViewSuggestedRightArrow = itemView.imgViewSuggestedRightArrow
    }
}
