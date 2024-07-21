package com.ucb.semifinal.springbootapiintegration.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ucb.semifinal.springbootapiintegration.R
import com.ucb.semifinal.springbootapiintegration.models.Subscription

class CustomListAdapterSubscription (private val context: Activity, private val list:List<Subscription>) :
    ArrayAdapter<Subscription>(context, R.layout.custom_list_adapter,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.custom_list_adapter,null)

        val customerEmail: TextView = view.findViewById(R.id.textViewName)
        val stationEmail: TextView = view.findViewById(R.id.textViewEmail)

        customerEmail.text = list[position].customerEmail
        stationEmail.text = "Station: ${ list[position].stationEmail }"

        return view

    }
}