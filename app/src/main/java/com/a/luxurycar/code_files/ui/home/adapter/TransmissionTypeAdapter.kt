package com.a.luxurycar.code_files.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.fragment.HomeFragment
import com.a.luxurycar.code_files.ui.home.model.home.TransmissionType
import kotlinx.android.synthetic.main.item_transmission_type.view.*

class TransmissionTypeAdapter(
    val fragment: HomeFragment,
    val arrTransmissionType: ArrayList<TransmissionType>
) : RecyclerView.Adapter<TransmissionTypeAdapter.ViewiewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewiewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transmission_type, parent, false);
        return ViewiewHolder(view);


    }

    override fun onBindViewHolder(holder: ViewiewHolder, position: Int) {

        val data = arrTransmissionType[position]
        holder.checkBoxManual.text = data.name

        holder.checkBoxManual.setOnClickListener {

            if( holder.checkBoxManual.isChecked){
                fragment.setTranmissionTypeListener(data.name,true)
            }else{
                fragment.setTranmissionTypeListener(data.name,false)
            }

        }



        // val item = list[position]
        // Picasso.get().load(item.image).into(holder.imgViewItem);
    }

    override fun getItemCount(): Int {
        return arrTransmissionType.size
    }


    inner class ViewiewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val checkBoxManual = itemView.checkBoxManual
    }
}