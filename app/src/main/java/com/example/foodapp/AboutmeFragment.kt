package com.example.foodapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.foodapp.databinding.FragmentAboutmeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AboutmeFragment : Fragment() {

    private var _binding: FragmentAboutmeBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences
    private val sharedPreferencesKey = "ABOUT_ME_DETAILS"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutmeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences(
            "AboutMePrefs",
            Context.MODE_PRIVATE
        )

        // Load saved details
        loadDetails()

        binding.fabAddDetails.setOnClickListener {
            showEditDialog()
        }
    }

    private fun showEditDialog() {
        val dialogView = layoutInflater.inflate(R.layout.edit_aboutme, null)

        val editTextName = dialogView.findViewById<EditText>(R.id.editTextName)
        val editTextFavoriteRecipes = dialogView.findViewById<EditText>(R.id.editTextFavoriteRecipes)
        val editTextFoodPhilosophy = dialogView.findViewById<EditText>(R.id.editTextFoodPhilosophy)

        // Set existing values
        editTextName.setText(binding.textName.text)
        editTextFavoriteRecipes.setText(binding.textFavoriteRecipes.text)
        editTextFoodPhilosophy.setText(binding.textFoodPhilosophy.text)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Edit Details")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, _ ->

                binding.textName.text = editTextName.text.toString()
                binding.textFavoriteRecipes.text = editTextFavoriteRecipes.text.toString()
                binding.textFoodPhilosophy.text = editTextFoodPhilosophy.text.toString()

                saveDetails()

                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun saveDetails() {
        val editor = sharedPreferences.edit()
        editor.putString("name", binding.textName.text.toString())
        editor.putString("favoriteRecipes", binding.textFavoriteRecipes.text.toString())
        editor.putString("foodPhilosophy", binding.textFoodPhilosophy.text.toString())
        editor.apply()
    }

    private fun loadDetails() {
        val name = sharedPreferences.getString("name", "Abdel")
        val favoriteRecipes = sharedPreferences.getString(
            "favoriteRecipes",
            "Favorite Recipes: Indulging in the aromatic delights of dishes like Tagine, Couscous with Seven Vegetables, and Bastilla, each bite a celebration of Morocco's culinary artistry."
        )
        val foodPhilosophy = sharedPreferences.getString(
            "foodPhilosophy",
            "Food Philosophy: Embracing the harmony of sweet and savory, the use of aromatic spices, and the importance of communal dining. In Morocco, food is not just sustenance; it's a vibrant expression of culture, hospitality, and the joy of sharing a meal with loved ones."
        )

        binding.textName.text = name
        binding.textFavoriteRecipes.text = favoriteRecipes
        binding.textFoodPhilosophy.text = foodPhilosophy
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
