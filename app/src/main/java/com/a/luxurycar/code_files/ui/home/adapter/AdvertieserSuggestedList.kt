package com.a.luxurycar.code_files.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a.luxurycar.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_advertiser.view.*


class AdvertieserSuggestedList(val context: Context): RecyclerView.Adapter<AdvertieserSuggestedList.ViewiewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewiewHolder {

        val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advertiser, parent, false);

        return ViewiewHolder(view);
    }

    override fun onBindViewHolder(holder: ViewiewHolder, position: Int) {

        val advertieserSuggestedList = AdvertiserCarListAdapter()
        holder.recyclerviewSuggestedList.adapter = advertieserSuggestedList
        holder.recyclerviewSuggestedList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)

        // Load an ad into the AdMob banner view.
        val adRequest = AdRequest.Builder().build()
        holder.adView.loadAd(adRequest)

        holder.adView.adListener = object : AdListener() {
            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                val toastMessage: String = "ad fail to load"
                Toast.makeText(context, toastMessage.toString(), Toast.LENGTH_LONG).show()

            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }

    inner class ViewiewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val recyclerviewSuggestedList = itemView.recyclerviewSuggestedList
        val adView = itemView.adView
    }
}
