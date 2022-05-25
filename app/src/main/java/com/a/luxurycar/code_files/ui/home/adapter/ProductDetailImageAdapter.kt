package com.a.luxurycar.code_files.ui.home.adapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.model.ProductDetailImageModel
import com.a.luxurycar.code_files.ui.home.model.ViewPagercurrentmageModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image.view.*

class ProductDetailImageAdapter(val context: Context, val list:ArrayList<ViewPagercurrentmageModel>):
    RecyclerView.Adapter<ProductDetailImageAdapter.ViewiewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewiewHolder {
        val view: View =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        return ViewiewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewiewHolder, position: Int) {
        val item = list[position]
        //SessionManager.setViewPagerItem(item.image.toString())
        item.image?.let { Picasso.get().load(it).into(holder.imgViewItem) };
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewiewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgViewItem = itemView.imgViewItem

    }
}