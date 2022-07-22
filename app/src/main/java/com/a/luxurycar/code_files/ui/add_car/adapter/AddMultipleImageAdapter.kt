package com.a.luxurycar.code_files.ui.add_car.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.add_car.fragment.AddCarStepOneFragment
import com.a.luxurycar.common.helper.AdapterSpinner
import com.a.luxurycar.common.requestresponse.Const
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_multiple_selected_image.view.*
import java.io.File

class AddMultipleImageAdapter(
    val context: Context,
    val listImage: ArrayList<String>,
    val fragment: AddCarStepOneFragment,
    val arrForImagePosition: ArrayList<HashMap<String, String>>,
) : RecyclerView.Adapter<AddMultipleImageAdapter.ViewHolder>() {

    /*var spinnerPosition = -1
    var isSpinnerTouched = false*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_multiple_selected_image, parent, false);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentPosition = position
        val item = listImage[position]


        Picasso.get().load(Uri.fromFile(File(item))).into(holder.imgViewItemCar)
        val adapter =
            AdapterSpinner(context, android.R.layout.simple_spinner_item, arrForImagePosition)
        adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        holder.spinnerPosition.setAdapter(adapter)
        holder.spinnerPosition.setSelection(currentPosition)



        holder.spinnerPosition.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {
                    currentPosition = position
                   Log.e("position", position.toString())
                    //holder.spinnerPosition.setSelection(position)
                }
            }
    }

    override fun getItemCount(): Int {
        return listImage.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgViewItemCar = itemView.imgViewItemCar
        val spinnerPosition = itemView.spinnerPositionValue
         //val edtTextFirstName = itemView.edtTextFirstName

    }

    /*var arrhasMapposition: ArrayList<HashMap<String, String>> = ArrayList()
    fun positionList(hasMapposition: ArrayList<HashMap<String, String>>) {
        arrhasMapposition = hasMapposition
        notifyDataSetChanged()
    }*/
}