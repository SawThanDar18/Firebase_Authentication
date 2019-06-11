package com.example.firebase_authentication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class HeroAdapter(context: Context, val layoutResId : Int, val heroList: List<Hero>) : ArrayAdapter<Hero>(context, layoutResId, heroList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutInflater.inflate(layoutResId, null)

        val result_tv = view.findViewById<TextView>(R.id.result_tv)
        val hero = heroList[position]
        result_tv.text = hero.name
        return view
    }
}