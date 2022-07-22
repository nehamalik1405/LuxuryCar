package com.a.luxurycar.code_files.ui.home.adapter

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.add_car.model.add_car_step_two.CarImage
import com.a.luxurycar.code_files.ui.home.model.ProductDetailImageModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image.view.*


class ProductDetailViewPagerAdapter(val context:Context, val list: ArrayList<ProductDetailImageModel>):RecyclerView.Adapter<ProductDetailViewPagerAdapter.ViewiewHolder>() {
    lateinit var productDetailImageModelList:ArrayList<ProductDetailImageModel>
    //var onItemClick:((ProductDetailImageModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewiewHolder {
        /* val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
          return  ViewiewHolder(view);*/

        val view: View = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        return ViewiewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewiewHolder, position: Int) {
        productDetailImageModelList  = arrayListOf()
        val item = list[position]

        //Toast.makeText(context,"$position", Toast.LENGTH_LONG).show()
        //SessionManager.setViewPagerItem(item.image.toString())
        item.image?.let { Picasso.get().load(it).into(holder.imgViewItem) };
        holder.imgViewItem.setOnClickListener {

            //onItemClick?.invoke(ProductDetailImageModel())
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner  class ViewiewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgViewItem = itemView.imgViewItem
    }
}