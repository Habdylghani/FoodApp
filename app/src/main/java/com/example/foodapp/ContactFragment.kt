package com.example.foodapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodapp.databinding.FragmentContactBinding


class ContactFragment : Fragment() {
    private var _binding: FragmentContactBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnPhone.setOnClickListener {
            dialChefPhoneNumber()
        }

        binding.btnEmail.setOnClickListener {
            sendEmailToChef()
        }
    }

    private fun dialChefPhoneNumber() {
        val phoneNumber = "123456789"
        val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        startActivity(dialIntent)
    }

    private fun sendEmailToChef() {
        val email = "chef@foodapp.com"
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
        startActivity(emailIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}