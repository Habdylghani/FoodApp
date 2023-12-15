package com.example.foodapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
        holder.imageView.setImageResource(recipelist[position].imageResourceId)



    }

    override fun getItemCount(): Int {
        return recipelist.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val imageView: ImageView = itemView.findViewById(R.id.imageView)
            var text_name = itemView.findViewById(R.id.name) as TextView
            var text_auth = itemView.findViewById(R.id.desc) as TextView




    }
}