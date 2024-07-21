package com.ucb.semifinal.springbootapiintegration.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ucb.semifinal.springbootapiintegration.R
import com.ucb.semifinal.springbootapiintegration.models.Inventory

class CustomListAdapterInventory (private val context: Activity, private val list:List<Inventory>) :
    ArrayAdapter<Inventory>(context, R.layout.custom_list_adapter,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.custom_list_adapter,null)

        val userName: TextView = view.findViewById(R.id.textViewName)
        val userEmail: TextView = view.findViewById(R.id.textViewEmail)

        userName.text = list[position].userEmail
        userEmail.text = "Gallons: ${ list[position].gallons }"

        return view

    }
}