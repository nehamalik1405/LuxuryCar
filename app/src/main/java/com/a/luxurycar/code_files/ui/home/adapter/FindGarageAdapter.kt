package com.a.luxurycar.code_files.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.fragment.FindGaragesFragment
import com.a.luxurycar.code_files.ui.seller_deshboard.model.find_garages.Data
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_find_garages.view.*

class FindGarageAdapter(val findGaragesFragment: FindGaragesFragment, var findGaragesList: ArrayList<Data>) :RecyclerView.Adapter<FindGarageAdapter.ViewHolder>() {


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
        if(data.rent_cars != null){
            holder.txtView53.text  = data.rent_cars.toString()
        }
        if(data.sale_cars != null){
            holder.txtView30.text  = data.sale_cars.toString()
        }
        holder.consLayoutTop.setOnClickListener {
            findGaragesFragment.navigateToGarageDetailPage(data.id.toString())
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
        val consLayoutTop = itemView.consLayoutTop
        val txtViewCityDubai = itemView.txtViewCityDubai
        val imgViewFindGarages = itemView.imgViewFindGarages
        val txtView53 = itemView.txtView53
        val txtView30 = itemView.txtView30

    }
}