package com.example.foodapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextUsername = findViewById(R.id.editTextUsername)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)

        sharedPreferences = getSharedPreferences("user_credentials", Context.MODE_PRIVATE)

        buttonLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val username = editTextUsername.text.toString()
        val password = editTextPassword.text.toString()


        if (isValidCredentials(username, password)) {

            saveCredentials(username, password)


            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
        }
    }


    private fun isValidCredentials(username: String, password: String): Boolean {
        return username == "user" && password == "password"
    }

    private fun saveCredentials(username: String, password: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }
}