package com.a.luxurycar.code_files.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.model.ImageModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image.view.*

class ImageAdapter(val context: Context, private val list:ArrayList<ImageModel>): RecyclerView.Adapter<ImageAdapter.ViewiewHolder>()  {
    //   (val context: Context, private val list:ArrayList<ImageModel>)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewiewHolder {
        /* val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
          return  ViewiewHolder(view);*/

        val view: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return ViewiewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewiewHolder, position: Int) {

        val item = list[position]
        item.image?.let { Picasso.get().load(it).into(holder.imgViewItem) };
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner  class ViewiewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgViewItem = itemView.imgViewItem
    }
}