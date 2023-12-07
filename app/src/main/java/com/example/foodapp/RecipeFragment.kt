package com.example.foodapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RecipeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe, container, false)

        val recipelist = ArrayList<Recipe>()
        recipelist.add(Recipe("Java","Horstman"))
        recipelist.add(Recipe("Kotlin","Joshua Bloch"))
        recipelist.add(Recipe("JavaFX","Herbert"))
        recipelist.add(Recipe("Android Essentials","Kathy"))
        recipelist.add(Recipe("Android Development","Bruce"))
        recipelist.add(Recipe("Kotlin Coding","Brain Goetz"))
        recipelist.add(Recipe("Java","Horstman"))
        recipelist.add(Recipe("Kotlin","Joshua Bloch"))
        recipelist.add(Recipe("JavaFX","Herbert"))
        recipelist.add(Recipe("Android Essentials","Kathy"))

        recipelist.add(Recipe("Kotlin","Joshua Bloch"))
        recipelist.add(Recipe("JavaFX","Herbert"))
        recipelist.add(Recipe("Android Essentials","Kathy"))
        recipelist.add(Recipe("Kotlin","Joshua Bloch"))
        recipelist.add(Recipe("JavaFX","Herbert"))
        recipelist.add(Recipe("Android Essentials","Kathy"))
        recipelist.add(Recipe("Kotlin","Joshua Bloch"))
        recipelist.add(Recipe("JavaFX","Herbert"))
        recipelist.add(Recipe("Android Essentials","Kathy"))
        // Inflate the layout for this fragment
        var adapter = MyRecViewAdapter(recipelist)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView1)

        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        return view
    }


}