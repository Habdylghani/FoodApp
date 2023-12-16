package com.example.foodapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class MealPlannerAdapter(
    private val mealList: MutableList<Meal>,
    private val onItemClick: (Meal) -> Unit
) : RecyclerView.Adapter<MealPlannerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayOfWeekTextView: TextView = itemView.findViewById(R.id.dayOfWeekTextView)
        val mealNameTextView: TextView = itemView.findViewById(R.id.mealNameTextView)
        val mealTypeTextView: TextView = itemView.findViewById(R.id.mealTypeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_meal, parent, false)
        val viewHolder = ViewHolder(itemView)

        itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClick(mealList[position])
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = mealList[position]
        holder.dayOfWeekTextView.text = meal.dayOfWeek
        holder.mealNameTextView.text = meal.mealName
        holder.mealTypeTextView.text = meal.mealType
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}
