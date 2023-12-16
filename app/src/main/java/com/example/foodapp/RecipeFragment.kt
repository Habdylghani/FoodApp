package com.example.foodapp

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
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

        adapter = MyRecViewAdapter(recipelist) { recipe ->
            showOptionsDialog(recipe)
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val fabAddRecipe: FloatingActionButton = view.findViewById(R.id.fabAddRecipe)

        fabAddRecipe.setOnClickListener {
            showAddRecipeDialog()
        }

        return view
    }

    private fun showOptionsDialog(recipe: Recipe) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Recipe Options")

        val options = arrayOf("Edit", "Delete")

        builder.setItems(options) { _, which ->
            when (which) {
                0 -> onEditRecipe(recipe)
                1 -> onDeleteRecipe(recipe)
            }
        }

        builder.setNegativeButton("Cancel") { _, _ -> }

        builder.create().show()
    }

    private fun onEditRecipe(recipe: Recipe) {
        val dialog = AddRecipeDialogFragment()

        val bundle = Bundle()
        bundle.putString("name", recipe.name)
        bundle.putString("desc", recipe.desc)
        bundle.putInt("imageResourceId", recipe.imageResourceId)
        dialog.arguments = bundle

        dialog.setAddRecipeListener(object : AddRecipeDialogFragment.AddRecipeListener {
            override fun onRecipeAdded(newRecipe: Recipe) {
                // Find the index of the existing recipe in the list
                val index = recipelist.indexOf(recipe)

                // Update the recipe with the new information
                if (index != -1) {
                    recipelist[index] = newRecipe
                    adapter.notifyDataSetChanged()

                    // Save updated recipe list to SharedPreferences
                    saveRecipes()
                }
            }
        })

        dialog.show(childFragmentManager, "AddRecipeDialog")
    }

    private fun onDeleteRecipe(recipe: Recipe) {
        recipelist.remove(recipe)
        adapter.notifyDataSetChanged()
        saveRecipes()
    }

    private fun showAddRecipeDialog() {
        val dialog = AddRecipeDialogFragment()
        dialog.setAddRecipeListener(this)
        dialog.show(childFragmentManager, "AddRecipeDialog")
    }

    override fun onRecipeAdded(recipe: Recipe) {
        recipelist.add(recipe)
        adapter.notifyDataSetChanged()
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
