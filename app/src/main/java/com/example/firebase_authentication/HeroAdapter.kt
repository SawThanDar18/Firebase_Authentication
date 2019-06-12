package com.example.firebase_authentication

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase

class HeroAdapter(val mContext: Context, val layoutResId : Int, val heroList: List<Hero>) : ArrayAdapter<Hero>(mContext, layoutResId, heroList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(mContext)
        val view : View = layoutInflater.inflate(layoutResId, null)

        val result_tv = view.findViewById<TextView>(R.id.result_tv)
        val update_tv = view.findViewById<TextView>(R.id.update_tv)

        val hero = heroList[position]
        result_tv.text = hero.name
        update_tv.setOnClickListener {
            showUpdateDialog(hero)
        }
        return view
    }

    fun showUpdateDialog(hero: Hero) {
        val builder =  AlertDialog.Builder(mContext)
        builder.setTitle("Update here")
        
        val inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.layout_update, null)

        val update_et = view.findViewById<EditText>(R.id.update_et)
        val update_rating_bar = view.findViewById<RatingBar>(R.id.update_rating_bar)

        builder.setView(view)
        
        update_et.setText(hero.name)
        update_rating_bar.rating = hero.rating.toFloat()
        
        builder.setPositiveButton("Update") { dialog, which ->
            val dbHero = FirebaseDatabase.getInstance().getReference("heroes")

            val name = update_et.text.toString().trim()
            if(name.isEmpty()){
                update_et.error = "Please enter a name"
                update_et.requestFocus()
                return@setPositiveButton
            }

            val hero = Hero(hero.id, name, update_rating_bar.rating.toInt())
            dbHero.child(hero.id).setValue(hero)
            Toast.makeText(mContext, "Updated", Toast.LENGTH_LONG).show()
        }

        builder.setNegativeButton("No") { dialog, which ->
             
        }

        val alert = builder.create()
        alert.show()
    }
}