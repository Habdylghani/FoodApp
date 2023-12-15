package com.example.foodapp

import java.io.Serializable

data class BlogPost(
    val title: String,
    val content: String,
    val author: String,
    val timestamp: Long
) : Serializable