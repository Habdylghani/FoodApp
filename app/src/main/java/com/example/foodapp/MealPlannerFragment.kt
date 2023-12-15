package com.example.foodapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Calendar

class MealPlannerFragment : Fragment() {
    private val mealList = mutableListOf<Meal>()
    private lateinit var mealPlannerAdapter: MealPlannerAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val sharedPreferencesKey = "MEAL_PLANNER_DATA"
    private lateinit var datePicker: DatePicker
    private lateinit var dateEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_meal_planner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences(
            "MealPlannerPrefs",
            Context.MODE_PRIVATE
        )

        mealPlannerAdapter = MealPlannerAdapter(mealList)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.adapter = mealPlannerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val fabPlanMeal: View = view.findViewById(R.id.fabPlanMeal)
        fabPlanMeal.setOnClickListener {
            showAddMealDialog()
        }

        loadMealsFromSharedPreferences()
    }

    private fun showAddMealDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Plan a Meal")

        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.dialog_add_meal, null)

        //datePicker = view.findViewById(R.id.datePicker)
        dateEditText = view.findViewById(R.id.dateEditText)
        dateEditText.setOnClickListener {
            showDatePicker()
        }

        val mealNameEditText = view.findViewById<EditText>(R.id.mealNameEditText)
        val mealTypeSpinner = view.findViewById<Spinner>(R.id.mealTypeSpinner)
        val mealTypes = arrayOf("Breakfast", "Lunch", "Dinner", "Snack") // Add more if needed
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, mealTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mealTypeSpinner.adapter = adapter

        builder.setView(view)

        builder.setPositiveButton("Plan") { _, _ ->
            val day = datePicker.dayOfMonth
            val month = datePicker.month
            val year = datePicker.year

            val selectedDate = "$day/${month + 1}/$year"
            dateEditText.setText(selectedDate)

            val mealName = mealNameEditText.text.toString()
            val mealType = mealTypeSpinner.selectedItem.toString()

            if (mealName.isNotEmpty() && mealType.isNotEmpty()) {
                val newMeal = Meal(selectedDate, mealName, mealType)
                mealList.add(newMeal)
                mealPlannerAdapter.notifyDataSetChanged()
                saveMealsToSharedPreferences(mealList)
            }
        }

        builder.setNegativeButton("Cancel") { _, _ ->

        }

        builder.create().show()
    }

    private fun saveMealsToSharedPreferences(meals: List<Meal>) {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(meals)
        editor.putString(sharedPreferencesKey, json)
        editor.apply()
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, day ->
            val selectedDate = "$day/${month + 1}/$year"
            dateEditText.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun loadMealsFromSharedPreferences() {
        val gson = Gson()
        val json = sharedPreferences.getString(sharedPreferencesKey, null)
        val type = object : TypeToken<List<Meal>>() {}.type

        val savedMeals: List<Meal> = gson.fromJson(json, type) ?: emptyList()
        mealList.addAll(savedMeals)
        mealPlannerAdapter.notifyDataSetChanged()
    }
}

