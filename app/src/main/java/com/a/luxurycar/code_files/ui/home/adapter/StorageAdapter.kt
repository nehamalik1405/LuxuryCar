package com.a.luxurycar.code_files.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.model.StorageModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_enquire.view.*

class StorageAdapter(val list:ArrayList<StorageModel>):RecyclerView.Adapter<StorageAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enquire, parent, false);
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        item.image?.let { Picasso.get().load(it).into(holder.imgViewAntiFlat) };
        holder.txtViewTitle.text = item.title.toString()

    }

    override fun getItemCount(): Int {
     return list.size
    }

   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

       val imgViewAntiFlat = itemView.imgViewAntiFlat
       val txtViewTitle = itemView.txtViewEnuire

    }
}