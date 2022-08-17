package com.a.luxurycar.code_files.ui.seller_deshboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.seller_deshboard.model.seller_car_list.Data
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.seller_item.view.*


class ForRentAdapter(val context:Context,val forRentList: ArrayList<Data>) : RecyclerView.Adapter<ForRentAdapter.ViewiewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewiewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item, parent, false);
        return ViewiewHolder(view);


    }

    override fun onBindViewHolder(holder: ViewiewHolder, position: Int) {

         val item = forRentList[position]

        for (index in item.carImages.indices){
            if (index == 0){
                Picasso.get().load(item.carImages[index].image).into(holder.imgViewItemCar)
            }
        }
        if (item.title !=null){
            holder.txtViewChevrolet.text = item.title
        }

        if (item.carYear !=null){
            holder.txtViewModel.text = item.carYear
        }

        if (item.runKms !=null){
            holder.txtViewKm.text = item.runKms+" KM"
        }
        if (item.price !=null){
            holder.txtViewPrice.text = "EAD "+item.price.toString()
        }

        holder.imgViewThreeDot.setOnClickListener {

            val popup = PopupMenu(context, holder.imgViewThreeDot);
            //Inflating the Popup using xml file
            popup.getMenuInflater()
                .inflate(R.menu.popup_edit_delete_menu, popup.getMenu());
            popup.setOnMenuItemClickListener { item ->
                Toast.makeText(
                    context,
                    "You Clicked : " + item.title,
                    Toast.LENGTH_SHORT
                ).show()
                true
            }
            popup.show(); //showing popup menu

        }
    }

    override fun getItemCount(): Int {
        return forRentList.size
    }


    inner class ViewiewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val imgViewItemCar = itemView.imgViewItemCar
       val imgViewThreeDot = itemView.imgViewThreeDot
       val txtViewChevrolet = itemView.txtViewChevrolet
       val txtViewModel = itemView.txtViewModel
       val txtViewKm = itemView.txtViewKm
       val txtViewPrice = itemView.txtViewPrice

    }
}