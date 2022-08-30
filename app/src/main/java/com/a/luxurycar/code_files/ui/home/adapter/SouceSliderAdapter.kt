package com.a.luxurycar.code_files.ui.home.adapter

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.model.cms.CmsbannerContent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_sourcing_slider.view.*

class SouceSliderAdapter(val arrcmsbannerContent: ArrayList<CmsbannerContent>) :
    RecyclerView.Adapter<SouceSliderAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sourcing_slider, parent, false);
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = arrcmsbannerContent[position]

        if (!data.logo.isNullOrEmpty()) {
            Picasso.get().load(data.logo).into(holder.imageViewTop)
        }

        if (!data.heading.isNullOrEmpty()) {
            holder.textViewHeading.text = data.heading
        }

        if (!data.content.isNullOrEmpty()) {
            holder.textViewDescription.text = Html.fromHtml(data.content).toString()
        }

    }

    override fun getItemCount(): Int {
        return arrcmsbannerContent.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewDescription = itemView.textViewDescription
        val imageViewTop = itemView.imageViewTop
        val textViewHeading = itemView.textViewHeading
    }
}