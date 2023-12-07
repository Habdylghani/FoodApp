package com.example.foodapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.util.ArrayList

class MyRecViewAdapter (var recipelist: ArrayList<Recipe>):
    RecyclerView.Adapter<MyRecViewAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyRecViewAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipes,parent,false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyRecViewAdapter.MyViewHolder, position: Int) {
        holder.text_name.text = recipelist[position].name
        holder.text_auth.text = recipelist[position].desc

    }

    override fun getItemCount(): Int {
        return recipelist.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var text_name: TextView
        var text_auth:TextView
        init{
            text_name = itemView.findViewById(R.id.name) as TextView
            text_auth = itemView.findViewById(R.id.desc) as TextView
        }

    }
}