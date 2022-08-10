package com.a.luxurycar.code_files.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.seller_deshboard.model.find_garages.Data
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_find_garages.view.*

class FindGarageAdapter(var findGaragesList: ArrayList<Data>) :RecyclerView.Adapter<FindGarageAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_garages, parent, false);
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = findGaragesList[position]
        if(!data.companyName.isNullOrEmpty()){
            holder.txtViewFirstChoiceGarage.text  = data.companyName
        }
        if(!data.countryCode.isNullOrEmpty()){
            holder.txtViewCityDubai.text  = data.countryCode
        }
        if(!data.image.isNullOrEmpty()){
            Picasso.get().load(data.image).into(holder.imgViewFindGarages)
        }


    }

    override fun getItemCount(): Int {
       return findGaragesList.size
    }

    fun getSearchGarageListList(searchFindGaragesList: ArrayList<Data>) {
        findGaragesList = searchFindGaragesList
        notifyDataSetChanged()
    }

    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtViewFirstChoiceGarage = itemView.txtViewFirstChoiceGarage
        val txtViewCityDubai = itemView.txtViewCityDubai
        val imgViewFindGarages = itemView.imgViewFindGarages

    }
}