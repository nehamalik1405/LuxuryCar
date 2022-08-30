package com.a.luxurycar.code_files.ui.home.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.model.cms.CmsbannerContent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_transport_export.view.*

class TransportExportAdapter(
    val context: Context,
    val arrListStepsList: ArrayList<CmsbannerContent>
) :RecyclerView.Adapter<TransportExportAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transport_export, parent, false);
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = arrListStepsList[position]
        if (data !=null){
             holder.txtViewNumber.text = data.id.toString()
             holder.txtViewTitle.text = data.heading
            val  plainText = Html.fromHtml(data.content).toString()
            holder.txtViewDescription.text = plainText

            Picasso.get().load(data.logo.toString()).into(holder.imgViewCar)
        }

    }

    override fun getItemCount(): Int {
       return arrListStepsList.size
    }
    inner class ViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){

        val txtViewStep = itemView.txtViewStep
        val txtViewNumber = itemView.txtViewNumber
        val imgViewCar = itemView.imgViewCar
        val txtViewTitle = itemView.txtViewTitle
        val txtViewDescription = itemView.txtViewDescription

    }
}