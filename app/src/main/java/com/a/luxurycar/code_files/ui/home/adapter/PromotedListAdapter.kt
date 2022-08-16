package com.a.luxurycar.code_files.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.model.home_response.Listt
import kotlinx.android.synthetic.main.item_advertiser.view.*
import kotlinx.android.synthetic.main.item_car.view.*

class PromotedListAdapter(val context: Context, val list:ArrayList<Listt>): RecyclerView.Adapter<PromotedListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = list[position]

        holder.txtViewChevrolet.text = itemData.title
        holder.txtViewModel.text = itemData.carYear
        holder.txtViewKm.text = itemData.runKms +" km"
        holder.txtViewPrice.text = "AED " +itemData.price
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtViewChevrolet = itemView.txtViewChevrolet
        val txtViewModel = itemView.txtViewModel
        val txtViewKm = itemView.txtViewKm
        val txtViewPrice = itemView.txtViewPrice

    }
}