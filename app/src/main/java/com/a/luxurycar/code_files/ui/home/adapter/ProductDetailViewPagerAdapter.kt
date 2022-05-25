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
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image.view.*


class ProductDetailViewPagerAdapter(val context:Context,val list:ArrayList<ProductDetailImageModel>):RecyclerView.Adapter<ProductDetailViewPagerAdapter.ViewiewHolder>() {
    lateinit var productDetailImageModelList:ArrayList<ProductDetailImageModel>
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

        //SessionManager.setViewPagerItem(item.image.toString())
        item.image?.let { Picasso.get().load(it).into(holder.imgViewItem) };
        holder.imgViewItem.setOnClickListener {
            //tis line for current item
         /* val currntItemList = mutableListOf<ProductDetailImageModel>()
            currntItemList.add(ProductDetailImageModel(item.image))*/

            // remove duplicate item in list
            for (listItem in list) {
                if (!productDetailImageModelList.contains(listItem)) {
                    productDetailImageModelList.add(listItem)
                    notifyItemChanged(position)

                }
            }

            val dialog = Dialog(context, android.R.style.Theme_Light)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.fragment_view_pager_details_image);
            val imageCarViewpager = dialog.findViewById<ViewPager2>(R.id.imgViewCarDialog)
            val tab = dialog.findViewById<TabLayout>(R.id.tab_layout)
            val productDetailViewPagerAdapter = ProductDetailViewPagerAdapter(context,
                productDetailImageModelList as ArrayList<ProductDetailImageModel>)
           // productDetailImageModelList.clear()
            imageCarViewpager.adapter= productDetailViewPagerAdapter
            TabLayoutMediator(tab, imageCarViewpager) { tab, position ->

            }.attach()

            val imageBack = dialog.findViewById<ImageView>(R.id.imgViewBack)

            //item.image?.let { Picasso.get().load(it).into(imageCarDialog) };
            dialog.show();
            imageBack.setOnClickListener {
                dialog.dismiss()
            }



        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner  class ViewiewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgViewItem = itemView.imgViewItem
    }
}