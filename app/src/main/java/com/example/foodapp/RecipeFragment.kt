package com.example.foodapp



import android.content.Context

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipeFragment : Fragment(), AddRecipeDialogFragment.AddRecipeListener {
    private val recipelist = ArrayList<Recipe>()
    private lateinit var adapter: MyRecViewAdapter
    private val sharedPreferencesKey = "recipe_prefs"
    private val recipeListKey = "recipe_list"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe, container, false)


        loadRecipes()

        adapter = MyRecViewAdapter(recipelist)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val fabAddRecipe: FloatingActionButton = view.findViewById(R.id.fabAddRecipe)

        fabAddRecipe.setOnClickListener {
            showAddRecipeDialog()
        }

        return view
    }

    private fun showAddRecipeDialog() {
        val dialog = AddRecipeDialogFragment()
        dialog.setAddRecipeListener(this)
        dialog.show(childFragmentManager, "AddRecipeDialog")
    }

    override fun onRecipeAdded(recipe: Recipe) {
        // Add the new recipe to the list
        recipelist.add(recipe)
        adapter.notifyDataSetChanged()

        // Save updated recipe list to SharedPreferences
        saveRecipes()
    }

    private fun saveRecipes() {
        val gson = Gson()
        val recipesJson = gson.toJson(recipelist)

        val sharedPreferences =
            requireActivity().getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(recipeListKey, recipesJson)
        editor.apply()
    }

    private fun loadRecipes() {
        val sharedPreferences =
            requireActivity().getSharedPreferences(sharedPreferencesKey, Context.MODE_PRIVATE)
        val recipesJson = sharedPreferences.getString(recipeListKey, null)

        if (!recipesJson.isNullOrBlank()) {
            val gson = Gson()
            val type = object : TypeToken<List<Recipe>>() {}.type
            val loadedRecipes: List<Recipe> = gson.fromJson(recipesJson, type)

            recipelist.addAll(loadedRecipes)
        }
    }
}