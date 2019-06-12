package com.example.firebase_authentication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    lateinit var name_et : EditText
    lateinit var rating_bar : RatingBar
    lateinit var save_btn : Button
    lateinit var reference : DatabaseReference
    lateinit var heroList: MutableList<Hero>
    lateinit var list_view : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        heroList = mutableListOf()
        reference = FirebaseDatabase.getInstance().getReference("heroes")

        name_et = findViewById(R.id.name_et)
        rating_bar = findViewById(R.id.rating_bar)
        save_btn = findViewById(R.id.save_btn)
        list_view = findViewById(R.id.list_view)

        save_btn.setOnClickListener {
            saveHero()
        }

        reference.addValueEventListener(object  : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext, "error", Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0!!.exists()){
                    heroList.clear()
                    for(h in p0.children){
                        val hero = h.getValue(Hero::class.java)
                        heroList.add(hero!!)
                    }

                    val adapter = HeroAdapter(this@MainActivity, R.layout.heroes, heroList)
                    list_view.adapter = adapter

                }
            }

        })
    }

    private fun saveHero(){

        val hero_name = name_et.text.toString().trim()

        if(hero_name.isEmpty()){
            name_et.error = "Please enter a name!!"
            return
        }

        val heroId = reference.push().key

        val hero = Hero(heroId!!, hero_name, rating_bar.rating.toInt())
        reference.child(heroId).setValue(hero).addOnCompleteListener {
            Toast.makeText(applicationContext, "Hero saved successfully", Toast.LENGTH_LONG).show()
        }

    }
}
