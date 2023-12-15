package com.example.foodapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import android.widget.Button
import android.widget.EditText


class AddRecipeDialogFragment : DialogFragment() {


    interface AddRecipeListener {
        fun onRecipeAdded(recipe: Recipe)
    }

    private var addRecipeListener: AddRecipeListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_recipe, container, false)


        val editTextRecipeName: EditText = view.findViewById(R.id.editTextRecipeName)
        val editTextRecipeDescription: EditText = view.findViewById(R.id.editTextRecipeDescription)
        val buttonAdd: Button = view.findViewById(R.id.buttonAdd)
        val buttonCancel: Button = view.findViewById(R.id.buttonCancel)


        buttonAdd.setOnClickListener {
            val recipeName = editTextRecipeName.text.toString()
            val recipeDescription = editTextRecipeDescription.text.toString()


            if (recipeName.isNotEmpty() && recipeDescription.isNotEmpty()) {
                val newRecipe = Recipe(recipeName, recipeDescription, R.drawable.food_jpeg)


                addRecipeListener?.onRecipeAdded(newRecipe)


                dismiss()
            } else {

            }
        }

        buttonCancel.setOnClickListener {

            dismiss()
        }

        return view
    }


    fun setAddRecipeListener(listener: AddRecipeListener) {
        addRecipeListener = listener
    }


}
