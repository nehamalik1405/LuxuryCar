package com.a.luxurycar.code_files.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.fragment.InspectingFragment
import com.a.luxurycar.code_files.ui.home.model.cms.CmsbannerContent
import kotlinx.android.synthetic.main.item_inspecting.view.*

class InspectingAdapter(
    val fragment: InspectingFragment,
    val arrInspectingLIst: ArrayList<CmsbannerContent>,
) :
    RecyclerView.Adapter<InspectingAdapter.ViewiewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewiewHolder {
        val view =
            LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inspecting, parent, false);
        return ViewiewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewiewHolder, position: Int) {
        val data = arrInspectingLIst[position]
        if (!data.heading.isNullOrEmpty()) {
            holder.textViewHeading.text = data.heading
        }
    }

    override fun getItemCount(): Int {
        return arrInspectingLIst.size
    }

    inner class ViewiewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewHeading = itemView.textViewHeading

    }


}