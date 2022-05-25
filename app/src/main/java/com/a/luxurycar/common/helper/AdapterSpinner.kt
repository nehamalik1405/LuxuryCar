package com.a.luxurycar.common.helper

import android.app.Activity
import android.content.Context
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.a.luxurycar.R
import com.a.luxurycar.common.requestresponse.Const


import java.util.ArrayList
import java.util.HashMap


class AdapterSpinner(internal var context: Context, txtViewResourceId: Int,
                     internal var list: ArrayList<HashMap<String, String>>) : ArrayAdapter<String>(context, txtViewResourceId) {

    override fun getItem(index: Int): String? {
        return list[index][Const.KEY_NAME]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getDropDownView(position: Int, cnvtView: View?, prnt: ViewGroup): View {
        return getCustomViewDropdown(position, cnvtView, prnt)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, convertView, parent)
    }

    fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        /*View mySpinner = inflater.inflate(R.layout.layout_spinner_dropdown, parent, false);*/
        val textView = View.inflate(context, R.layout.item_spinner, null) as TextView

        textView.setTextColor(ContextCompat.getColor(context, R.color.black))

        val events = list[position]

        if (events[Const.KEY_NAME] != null)
            textView.text = events[Const.KEY_NAME].toString()


        return textView
    }

    fun getCustomViewDropdown(position: Int, convertView: View?, parent: ViewGroup): View {

        val textView = View.inflate(context, R.layout.simple_spinner_drop_down_item, null) as TextView

        val events = list[position]

        if (events[Const.KEY_NAME] != null)
            textView.text = events[Const.KEY_NAME].toString()

        return textView
    }

    override fun getCount(): Int {
        return list.size
    }
}
