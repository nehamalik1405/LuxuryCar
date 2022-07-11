package com.a.luxurycar.code_files.ui.add_car.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.add_car.fragment.Image
import com.a.luxurycar.code_files.ui.add_car.model.AddMultipleImageModel
import com.a.luxurycar.code_files.ui.home.adapter.CarListAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_multiple_selected_image.view.*
import java.io.File

class AddMultipleImageAdapter(val context: Context,val listImage:ArrayList<Image>):RecyclerView.Adapter<AddMultipleImageAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_multiple_selected_image, parent, false);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listImage[position]
        holder.edtTextFirstName.text = item.position.toString()
        Picasso.get().load(Uri.fromFile(File(item.image))).into(holder.imgViewItemCar)
    }

    override fun getItemCount(): Int {
        return listImage.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imgViewItemCar = itemView.imgViewItemCar
        val edtTextFirstName = itemView.edtTextFirstName
    }
}