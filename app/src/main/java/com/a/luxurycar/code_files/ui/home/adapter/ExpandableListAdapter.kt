package com.a.luxurycar.code_files.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import kotlinx.android.synthetic.main.item_expandable_card.view.*

class ExpandableListAdapter():RecyclerView.Adapter<ExpandableListAdapter.Viewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expandable_card, parent, false);
        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
    holder.cardViewExpandable.setOnClickListener {

        if( holder.txtViewExpandable.isVisible){
            holder.imgViewDropDown.setImageResource(R.mipmap.ic_down_arrow)
            holder.txtViewExpandable.visibility = View.GONE
        }
        else{
            holder.imgViewDropDown.setImageResource(R.mipmap.ic_top_arrow)
            holder.txtViewExpandable.visibility = View.VISIBLE
        }


    }
    }

    override fun getItemCount(): Int {
       return 5
    }

  inner class Viewholder(itemView: View):RecyclerView.ViewHolder(itemView) {
      val cardViewExpandable = itemView.cardViewExpandable
      val txtViewExpandable = itemView.txtViewExpandable
      val imgViewDropDown = itemView.imgViewDropDown
    }
}