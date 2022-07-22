package com.a.luxurycar.code_files.ui.home.adapter

import android.app.Dialog
import android.content.Context
import android.view.*
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.add_car.model.add_car_step_two.CarImage
import com.a.luxurycar.code_files.ui.home.model.ProductDetailImageModel

import com.jsibbold.zoomage.ZoomageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_image.view.*
import kotlinx.android.synthetic.main.item_image_dialog.view.*

class ProductDetailImageAdapter(
    val context: Context,
    val list: ArrayList<ProductDetailImageModel>,
):
    RecyclerView.Adapter<ProductDetailImageAdapter.ViewiewHolder>() {
  var   currentPosition = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewiewHolder {
        val view: View =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_dialog, parent, false)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        return ViewiewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewiewHolder, position: Int) {
        val item = list[position]
        //holder.imgViewItem.doubleTapToZoom
        holder.imgViewItem.setScaleRange(0.5f,4.0f)
        item.image?.let { Picasso.get().load(it).into(holder.imgViewItem)}

    }

    override fun getItemCount(): Int {
        return list.size
    }



    class ViewiewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgViewItem = itemView.imgViewDialogItem as ZoomageView

    }

}