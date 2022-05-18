package com.a.luxurycar.code_files.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.model.advertiser_suggersted_list.Listt
import com.a.luxurycar.code_files.ui.home.model.advertiser_suggersted_list.SuggestedCars
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import kotlinx.android.synthetic.main.item_advertiser.view.*
import kotlinx.android.synthetic.main.item_car.view.*


class SuggestedListAdapter(val context: Context, val list:ArrayList<Listt>): RecyclerView.Adapter<SuggestedListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestedListAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return ViewHolder(view);

    }

    override fun onBindViewHolder(holder: SuggestedListAdapter.ViewHolder, position: Int) {

        val itemData = list[position]

        holder.txtViewChevrolet.text = itemData.name
        holder.txtViewModel.text = itemData.carYear
        holder.txtViewKm.text = itemData.runKms
        holder.txtViewPrice.text = itemData.price

    }

    override fun getItemCount(): Int {
        return list.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val txtViewChevrolet = itemView.txtViewChevrolet
       val txtViewModel = itemView.txtViewModel
       val txtViewKm = itemView.txtViewKm
       val txtViewPrice = itemView.txtViewPrice
    }

}