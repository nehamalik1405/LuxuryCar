package com.a.luxurycar.code_files.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.fragment.CarListFragment
import com.a.luxurycar.code_files.ui.home.fragment.CarSearchFragment
import com.a.luxurycar.code_files.ui.home.fragment.InspectingFragment
import com.a.luxurycar.code_files.ui.home.model.car_list_response.DataX
import com.a.luxurycar.code_files.ui.home.model.cms.CmsbannerContent
import com.a.luxurycar.code_files.ui.home.model.search_ads_response.SearchData
import kotlinx.android.synthetic.main.item_car.view.*
import kotlinx.android.synthetic.main.item_inspecting.view.*

class SearchCarAdapter(val fragment: CarSearchFragment, val arrCarList: ArrayList<SearchData>) :
    RecyclerView.Adapter<SearchCarAdapter.ViewiewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewiewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return ViewiewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewiewHolder, position: Int) {

        val item = arrCarList[position]

        if (item.title != null) {
            holder.txtViewChevrolet.text = item.title
        }
        if (item.carYear != null) {
            holder.txtViewModel.text = item.carYear
        }
        if (item.runKms != null) {
            holder.txtViewKm.text = item.runKms
        }
        if (item.price != null) {
            holder.txtViewPrice.text = item.price
        }


       holder.cardViewItemCar.setOnClickListener {
           if(!item.id.toString().isNullOrEmpty()){
               fragment.navigateToDetailPage(item.id.toString())
           }

        }

    }

    override fun getItemCount(): Int {
        return arrCarList.size
    }


    inner class ViewiewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardViewItemCar = itemView.cardViewItemCar
        val imgViewItemCar = itemView.imgViewItemCar
        val txtViewChevrolet = itemView.txtViewChevrolet
        val txtViewModel = itemView.txtViewModel
        val txtViewKm = itemView.txtViewKm
        val txtViewPrice = itemView.txtViewPrice
        //val recyclerviewSuggestedList = itemView.recyclerviewSuggestedList
    }


}
