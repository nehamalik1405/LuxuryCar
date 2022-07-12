package com.a.luxurycar.code_files.ui.add_car.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.a.luxurycar.code_files.repository.AddCarStepThreeRepository
import com.a.luxurycar.code_files.ui.add_car.fragment.AddCarStepThreeFragment
import kotlinx.android.synthetic.main.item_plan_list.view.*
import com.a.luxurycar.code_files.ui.add_car.model.step_three_listing_plan.Data
import com.a.luxurycar.code_files.view_model.AddCarStepThreeViewModel
import com.a.luxurycar.common.requestresponse.ApiService
import com.a.luxurycar.common.utils.SetTextColor

class SellerPlanListAdapter(
    val context: Context,
    val list: ArrayList<Data>,
    val addCarStepThreeFragment: AddCarStepThreeFragment
) :
    RecyclerView.Adapter<SellerPlanListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_plan_list, parent, false);
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = list[position]

        holder.txtViewBasic.text = item.title
        holder.txtViewPrice.text = item.price
        holder.txtViewContent.setText(Html.fromHtml(item.content).toString())
        holder.btnSelectPlan.setText("Select " + item.title)


        if (item.isSelected) {
            holder.consLayoutBasicPlan.setBackgroundResource(R.color.green)
            holder.consLayoutBasicCurve.setBackgroundResource(R.drawable.drawable_arc_for_yellow_background)
            holder.txtViewContent.SetTextColor(R.color.white)
            holder.consLayoutFree.setBackgroundResource(R.color.white)
            holder.txtViewPrice.SetTextColor(R.color.green)


            // button color change on card selection
            holder.btnSelectPlan.setBackgroundResource(R.drawable.drawable_selected_premium_background)
            //  binding.btnSelectPremium.setBackgroundResource(R.drawable.drawable_background_border)

        } else {
            holder.consLayoutBasicPlan.setBackgroundResource(R.color.cardview_color)
            holder.consLayoutBasicCurve.setBackgroundResource(R.drawable.drawable_arc)
            holder.txtViewContent.SetTextColor(R.color.description_color)
            holder.consLayoutFree.setBackgroundResource(R.color.color_background_black)
            holder.txtViewPrice.SetTextColor(R.color.white)

            // button color change on card selection
            holder.btnSelectPlan.setBackgroundResource(R.drawable.drawable_background_border)
        }

        holder.consLayoutBasicPlan.setOnClickListener {
            for (i in 0..list.size - 1) {
                list[i].isSelected = false
            }
            item.isSelected = true
            list[position] = item
           notifyItemChanged(position)
        }
        holder.btnSelectPlan.setOnClickListener {

            if (item.isSelected) {
                val id = item.id
                val status = item.status
                addCarStepThreeFragment.onItemClickListner(id.toString(), status)
            }


        }

    }



    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtViewBasic = itemView.txtViewBasic
        val txtViewPrice = itemView.txtViewPrice
        val txtViewContent = itemView.txtViewContent
        val btnSelectPlan = itemView.btnSelectPlan
        val consLayoutBasicPlan = itemView.consLayoutBasicPlan
        val consLayoutBasicCurve = itemView.consLayoutBasicCurve
        val consLayoutFree = itemView.consLayoutFree


    }


}