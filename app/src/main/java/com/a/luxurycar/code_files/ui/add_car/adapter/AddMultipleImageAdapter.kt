package com.a.luxurycar.code_files.ui.add_car.adapter

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.add_car.fragment.AddCarStepOneFragment
import com.a.luxurycar.code_files.ui.add_car.model.ImageIndexSelectionModel
import com.a.luxurycar.code_files.ui.seller_deshboard.adapter.RecyclerViewDialogAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_multiple_selected_image.view.*
import java.io.File


class AddMultipleImageAdapter(
    val context: Context,
    val listImage: ArrayList<ImageIndexSelectionModel>,
    val fragment: AddCarStepOneFragment,
    val arrForImagePosition: ArrayList<String>,

    ) : RecyclerView.Adapter<AddMultipleImageAdapter.ViewHolder>() {

    /*var spinnerPosition = -1
    var isSpinnerTouched = false*/

    var previousIndex = -1
    var currentIndex = -1
    var currentIndexValue = -1
    var previousIndexValue = -1
    var indexReturn = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_multiple_selected_image, parent, false);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = listImage[position]

        Picasso.get().load(Uri.fromFile(File(item.imagePath))).into(holder.imgViewItemCar)
        holder.txtViewFirstName.text = ""+item.index




        holder.txtViewFirstName.setOnClickListener {

            previousIndexValue = holder.txtViewFirstName.text.toString().trim().toInt()
            showImageIndexList()


        }


        /*val adapter =
            AdapterSpinner(context, android.R.layout.simple_spinner_item, arrForImagePosition)
        adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item)
        holder.spinnerPosition.setAdapter(adapter)
        holder.spinnerPosition.setSelection(item.index-1, false)
*/


      /*  holder.spinnerPosition.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long,
                ) {

                    previousIndex = currentIndex
                    currentIndex = position
                    var previousSelectedValue = 0
                    var currentSelectedValue = 0
                    if(previousIndex != -1) {
                        previousSelectedValue = listImage[previousIndex].index
                    }

                    if(currentIndex != -1) {
                        currentSelectedValue = listImage[currentIndex].index
                    }

                    var previousSelectedValueIndex = 0
                    var currentSelectedValueIndex = 0
                    for (i in 0 until listImage.size) {
                        if(listImage[i].index == previousSelectedValue) {
                            previousSelectedValueIndex = i
                        }
                        if(listImage[i].index == currentSelectedValue) {
                            currentSelectedValueIndex = i
                        }
                    }

                    if(previousIndex != currentIndex) {
                        listImage[currentSelectedValueIndex].index = previousSelectedValueIndex
                        listImage[previousSelectedValueIndex].index = currentSelectedValueIndex
                        notifyDataSetChanged()
                    }
                }
            }*/
    }

    fun updateIndexAccToSelection() {
        var previousSelectedValueIndex = 0
        var currentSelectedValueIndex = 0
        for (i in 0 until listImage.size) {
            if(listImage[i].index == previousIndexValue) {
                previousSelectedValueIndex = i
            }
            if(listImage[i].index == currentIndexValue) {
                currentSelectedValueIndex = i
            }
        }


        listImage[currentSelectedValueIndex].index = previousIndexValue
        listImage[previousSelectedValueIndex].index = currentIndexValue
        notifyDataSetChanged()
    }


    fun showImageIndexList() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_position)
        dialog.getWindow()?.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        val recyclerView = dialog.findViewById<RecyclerView>(R.id.recyclerViewImagePosition)
        val adapter = RecyclerViewDialogAdapter(arrForImagePosition,this,dialog)
        recyclerView.adapter = adapter
        recyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(context
            ) { view, position ->
                currentIndexValue = arrForImagePosition[position].toInt()
                updateIndexAccToSelection()
                dialog.dismiss()
            }
        )
        //dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

     fun setItemClickPosition(position: String):String{
         indexReturn = position.toInt()
         return indexReturn.toString()
    }


    override fun getItemCount(): Int {
        return listImage.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgViewItemCar = itemView.imgViewItemCar
       //val spinnerPosition = itemView.spinnerPositionValue
         val txtViewFirstName = itemView.textViewFirstName

    }

    /*var arrhasMapposition: ArrayList<HashMap<String, String>> = ArrayList()
    fun positionList(hasMapposition: ArrayList<HashMap<String, String>>) {
        arrhasMapposition = hasMapposition
        notifyDataSetChanged()
    }*/
}