package com.a.luxurycar.code_files.ui.add_car.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.add_car.model.AddMultipleImageModel
import com.a.luxurycar.code_files.ui.home.adapter.CarListAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_multiple_selected_image.view.*

class AddMultipleImageAdapter(val context: Context,val listImage:ArrayList<AddMultipleImageModel>):RecyclerView.Adapter<AddMultipleImageAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_multiple_selected_image, parent, false);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listImage[position]
        holder.edtTextFirstName.text = item.name
        item.image?.let { Picasso.get().load(it).into(holder.imgViewItemCar) };

    }

    override fun getItemCount(): Int {
        return listImage.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imgViewItemCar = itemView.imgViewItemCar
        val edtTextFirstName = itemView.edtTextFirstName
    }
}