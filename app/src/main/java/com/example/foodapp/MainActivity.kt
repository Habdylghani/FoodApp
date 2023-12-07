package com.example.foodapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val myPageAdapter = MyPageAdapter(this)

        binding.vpager.adapter = myPageAdapter
        binding.tlayout.tabGravity = TabLayout.GRAVITY_FILL
        TabLayoutMediator(binding.tlayout,
            binding.vpager){tab,position->
            when(position){
                0->{

                    tab.setIcon(R.drawable.recipes)
                }
                1->{

                    tab.setIcon(R.drawable.mealplanner)
                }
                2->{

                    tab.setIcon(R.drawable.blog)
                }
                3->{

                    tab.setIcon(R.drawable.contact)
                }
                4->{

                    tab.setIcon(R.drawable.about)
                }
            }
        }.attach()
    }
}