package com.a.luxurycar.code_files.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.model.cms.CmsbannerContent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_enquire.view.*

class StorageAdapter(val arrEnquiesList: ArrayList<CmsbannerContent>):RecyclerView.Adapter<StorageAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enquire, parent, false);
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = arrEnquiesList[position]
        item.logo?.let { Picasso.get().load(it).into(holder.imgViewAntiFlat) };
        holder.txtViewTitle.text = item.heading

    }

    override fun getItemCount(): Int {
     return arrEnquiesList.size
    }

   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

       val imgViewAntiFlat = itemView.imgViewAntiFlat
       val txtViewTitle = itemView.txtViewEnuire

    }
}