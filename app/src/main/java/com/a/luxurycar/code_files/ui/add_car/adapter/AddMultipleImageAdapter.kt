package com.a.luxurycar.code_files.ui.add_car.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.add_car.fragment.AddCarStepOneFragment
import com.a.luxurycar.common.helper.AdapterSpinner
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_multiple_selected_image.view.*
import java.io.File

class AddMultipleImageAdapter(
    val context: Context,
    val listImage: ArrayList<String>,
    val fragment: AddCarStepOneFragment,
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

        val item = listImage[position]
        Picasso.get().load(Uri.fromFile(File(item))).into(holder.imgViewItemCar)
       /* val adapter =
            AdapterSpinner(context, android.R.layout.simple_spinner_item, arrhasMapposition)
        adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        holder.spinnerPosition.setAdapter(adapter)*/


      /*  holder.spinnerPosition.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {

                isSpinnerTouched = true

                Toast.makeText(context,"ontouch called", Toast.LENGTH_LONG).show()
                return true
            }

        })*/




       /* holder.spinnerPosition.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long, ) {

                   *//* if (!isSpinnerTouched)
                        return*//*

                    *//*if (spinnerPosition != position) {*//*
                        fragment.onSpinnerItemOnClick(position)
                       *//* spinnerPosition = position
                    }*//*


                }
            }*/
    }

    override fun getItemCount(): Int {
        return listImage.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgViewItemCar = itemView.imgViewItemCar
       // val spinnerPosition = itemView.spinnerPosition
         val edtTextFirstName = itemView.edtTextFirstName

    }

    /*var arrhasMapposition: ArrayList<HashMap<String, String>> = ArrayList()
    fun positionList(hasMapposition: ArrayList<HashMap<String, String>>) {
        arrhasMapposition = hasMapposition
        notifyDataSetChanged()
    }*/
}