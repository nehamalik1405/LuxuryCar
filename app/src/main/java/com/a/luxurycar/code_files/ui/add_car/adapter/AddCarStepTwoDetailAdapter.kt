package com.a.luxurycar.code_files.ui.add_car.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.add_car.model.add_car_step_two.Detail
import kotlinx.android.synthetic.main.item_details.view.*


class AddCarStepTwoDetailAdapter(val detailList: ArrayList<Detail>) :RecyclerView.Adapter<AddCarStepTwoDetailAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_details, parent, false);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     val data = detailList[position]
        holder.txtViewMake.text  = data.title
        holder.txtViewMakeResult.text = data.name
    }

    override fun getItemCount(): Int {
      return detailList.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val txtViewMake = itemView.txtViewMake
        val txtViewMakeResult = itemView.txtViewMakeResult
    }
}