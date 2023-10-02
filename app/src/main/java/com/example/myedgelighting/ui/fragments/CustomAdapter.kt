package com.example.myedgelighting.ui.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myedgelighting.R


class CustomAdapter(context: Context?, personImages: ArrayList<*>) {
    var personImages: ArrayList<*>? = null
    var context: Context? = null

    fun CustomAdapter(context: Context, personNames: ArrayList<*>, personImages: ArrayList<*>) {

    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder? {
        // infalte the item Layout
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.single_alphabet, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(v)
    }
//    fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        // set the data in items
//        // implement setOnClickListener event on item view.
////        holder.itemView.setOnClickListener {
////            // open another activity on item click
////            val intent = Intent(context, SecondActivity::class.java)
////            intent.putExtra("image", personImages.get(position)) // put image data in Intent
////            context.startActivity(intent) // start Intent
////        }
}

//    fun getItemCount(): Int {
//       // return personNames.size()
//    }

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    // init the item view's

}


