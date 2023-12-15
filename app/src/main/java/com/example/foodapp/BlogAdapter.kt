package com.example.foodapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BlogAdapter(private val blogList: List<BlogPost>) :
    RecyclerView.Adapter<BlogAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_blog_post, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBlogPost = blogList[position]

        holder.titleTextView.text = currentBlogPost.title
        holder.contentTextView.text = currentBlogPost.content
        holder.authorTextView.text = currentBlogPost.author
    }

    override fun getItemCount(): Int {
        return blogList.size
    }
}
