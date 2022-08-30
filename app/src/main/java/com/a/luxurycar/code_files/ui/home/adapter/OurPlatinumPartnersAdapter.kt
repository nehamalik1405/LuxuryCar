package com.a.luxurycar.code_files.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater

import android.view.View

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.fragment.HomeFragment
import com.a.luxurycar.code_files.ui.home.model.home_response.PlatinumPartnersList
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_platinum_partners.view.*


class OurPlatinumPartnersAdapter(
    val context: Context,
    val list: ArrayList<PlatinumPartnersList>,
    val homeFragment: HomeFragment
): RecyclerView.Adapter<OurPlatinumPartnersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_platinum_partners, parent, false);
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = list[position]

        Picasso.get().load(itemData.image).into(holder.imgViewItemCar)
        holder.cardItemPlatinumPartners.setOnClickListener {
           homeFragment.navigateToFindGaragesFragment(itemData.id.toString())
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }
    inner  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardItemPlatinumPartners = itemView.cardItemPlatinumPartners
        val imgViewItemCar = itemView.imgViewItemCar
    }
}