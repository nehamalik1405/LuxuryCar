package com.a.luxurycar.code_files.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.ui.home.fragment.GarageDetailsFragment
import com.a.luxurycar.code_files.ui.home.model.garage_response.GarageCar

import com.a.luxurycar.common.helper.SessionManager
import com.a.luxurycar.common.requestresponse.Const
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.seller_item.view.*


class FindGarageListForSaleAdapter(
    val context: Context,
    val forSaleList: ArrayList<GarageCar>,
    val sellerHomeFragment: GarageDetailsFragment
) :RecyclerView.Adapter<FindGarageListForSaleAdapter.ViewiewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewiewHolder {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item, parent, false);
        return ViewiewHolder(view);


    }

    override fun onBindViewHolder(holder: ViewiewHolder, position: Int) {

        val item = forSaleList[position]
        val idForDeleteItem = item.id
        val data = SessionManager.getUserData()

            if(data?.role.equals(Const.KEY_BUYER)){
                holder.imgViewThreeDot.visibility = View.GONE
            }


        for (i in item.carImages?.indices!!) {
            if (i == 0) {
                Picasso.get().load(item.carImages?.get(i)?.image).into(holder.imgViewItemCar)
            }
        }
        if (item.title != null) {
            holder.txtViewChevrolet.text = item.title
        }

        if (item.carYear != null) {
            holder.txtViewModel.text = item.carYear
        }

        if (item.runKms != null) {
            holder.txtViewKm.text = item.runKms + " KM"
        }
        if (item.price != null) {
            holder.txtViewPrice.text = "EAD " + item.price.toString()
        }
        holder.imgViewThreeDot.setOnClickListener {

            val popup = PopupMenu(context, holder.imgViewThreeDot);
            //Inflating the Popup using xml file
            popup.getMenuInflater()
                .inflate(R.menu.popup_edit_delete_menu, popup.getMenu());
            popup.setOnMenuItemClickListener { item ->
                if(item.title.equals("Make Premium Ad")){
                    Toast.makeText(
                        context,
                        "You Clicked : 1",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                if(item.title.equals("Edit")){
                    Toast.makeText(
                        context,
                        "You Clicked : 2",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                if(item.title.equals("Delete")){
                    val builder1 = AlertDialog.Builder(context)
                    builder1.setMessage("Are You Sure You Want to delete item?")
                    builder1.setCancelable(true)
                    builder1.setPositiveButton("Yes") { dialog, id ->

                    }
                    builder1.setNegativeButton("No")
                    { dialog, id -> dialog.cancel() }
                    val alert11 = builder1.create()
                    alert11.show()
                }

                true
            }

            popup.show(); //showing popup menu

        }

    }





    override fun getItemCount(): Int {
        return forSaleList.size
    }


    inner class ViewiewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtViewChevrolet = itemView.txtViewChevrolet
        val imgViewItemCar = itemView.imgViewItemCar
        val txtViewModel = itemView.txtViewModel
        val txtViewKm = itemView.txtViewKm
        val txtViewPrice = itemView.txtViewPrice
        val imgViewThreeDot = itemView.imgViewThreeDot


    }
}