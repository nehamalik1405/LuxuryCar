package com.a.luxurycar.code_files.ui.seller_deshboard.adapter

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.add_car.adapter.AddMultipleImageAdapter
import kotlinx.android.synthetic.main.item_list_picture_position.view.*

class RecyclerViewDialogAdapter(
    val arrForImagePosition: ArrayList<String>,
    val addMultipleImageAdapter: AddMultipleImageAdapter,
    val dialog: Dialog,

) :RecyclerView.Adapter<RecyclerViewDialogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_picture_position, parent, false);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         val item = arrForImagePosition[position]
         holder.txtViewPosition.text = item

        /*holder.txtViewPosition.setOnClickListener {
            dialog.dismiss()
        }*/


    }

    override fun getItemCount(): Int {
     return arrForImagePosition.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val txtViewPosition = itemView.txtViewPosition
    }
}