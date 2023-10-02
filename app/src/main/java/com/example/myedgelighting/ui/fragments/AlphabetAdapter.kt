package com.example.myedgelighting.ui.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myedgelighting.R
import com.example.myedgelighting.custom_edge_view.EdgeOverlayView
import com.example.myedgelighting.utils.CommonKeys
import com.example.myedgelighting.utils.MySharedPrefs


class AlphabetAdapter(val context: Context, private val itemList: ArrayList<TricksModel>) :
    RecyclerView.Adapter<AlphabetAdapter.ImageItemViewHolder>() {

    var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.single_alphabet, parent, false)
        return ImageItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.title.text = item.value

        // Update selected state based on the position
        holder.itemView.isSelected = selectedPosition == position
        holder.ivSelected.visibility =
            if (holder.itemView.isSelected) View.VISIBLE else View.INVISIBLE

        holder.itemView.setOnClickListener {
            // Update selected position and refresh the adapter
            selectedPosition = position
            notifyDataSetChanged()


            val customString = item.value // Set your desired custom string here

            MySharedPrefs(context).setStringPref(CommonKeys.CUSTOM_STRING, customString)
            MySharedPrefs(context).setIntPref(CommonKeys.LAST_SELECTED_POSITION, selectedPosition)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ImageItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.ivAlphabet)
        val ivSelected: ImageView = itemView.findViewById(R.id.ivSelected)
    }
}

