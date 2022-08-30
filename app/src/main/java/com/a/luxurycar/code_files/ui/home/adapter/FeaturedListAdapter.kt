package com.a.luxurycar.code_files.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.fragment.HomeFragment
import com.a.luxurycar.code_files.ui.home.model.home_response.Listt
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_car.view.*

class FeaturedListAdapter(
    val context: Context,
    val list: ArrayList<Listt>,
   val homeFragment: HomeFragment
): RecyclerView.Adapter<FeaturedListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = list[position]
        holder.cardViewItemCar.setOnClickListener {
            homeFragment.navigateToProductDetailPage(itemData.id.toString())
        }

        for(item in itemData.carImages.indices){
            if(item == 0){
                Picasso.get().load(itemData.carImages[item].image).into(holder.imgViewItemCar)
            }
        }
        if(!itemData.title.isNullOrEmpty()){
            holder.txtViewChevrolet.text = itemData.title
        }
        if(!itemData.carYear.isNullOrEmpty()){
            holder.txtViewModel.text = itemData.carYear
        }
        if(!itemData.runKms.isNullOrEmpty()){
            holder.txtViewKm.text = itemData.runKms +" km"
        }
        if(!itemData.price.isNullOrEmpty()){
            holder.txtViewPrice.text = "AED " +itemData.price
        }
        holder.textViewPremium.text = "F"
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtViewChevrolet = itemView.txtViewChevrolet
        val imgViewItemCar = itemView.imgViewItemCar
        val cardViewItemCar = itemView.cardViewItemCar
        val txtViewModel = itemView.txtViewModel
        val txtViewKm = itemView.txtViewKm
        val txtViewPrice = itemView.txtViewPrice
        val textViewPremium = itemView.textViewPremium

    }
}