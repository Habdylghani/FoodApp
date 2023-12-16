package com.example.foodapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BlogAdapter(
    private val blogList: MutableList<BlogPost>,
    private val onItemClick: (BlogPost) -> Unit
) : RecyclerView.Adapter<BlogAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_blog_post, parent, false)
        val viewHolder = ViewHolder(itemView)

        itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClick(blogList[position])
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val blogPost = blogList[position]
        holder.titleTextView.text = blogPost.title
        holder.contentTextView.text = blogPost.content
    }

    override fun getItemCount(): Int {
        return blogList.size
    }
}
