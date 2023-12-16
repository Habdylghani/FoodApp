package com.example.foodapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.foodapp.databinding.AddBlogBinding

class AddBlogPostDialogFragment : DialogFragment() {

    interface AddBlogPostListener {
        fun onBlogPostAdded(blogPost: BlogPost)
    }

    var addBlogPostListener: AddBlogPostListener? = null
    private var _binding: AddBlogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddBlogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddPost.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val content = binding.editTextContent.text.toString()
            val author = binding.editTextAuthor.text.toString()

            if (title.isNotEmpty() && content.isNotEmpty() && author.isNotEmpty()) {
                val timestamp = System.currentTimeMillis()
                val blogPost = BlogPost(title, content, author, timestamp)

                addBlogPostListener?.onBlogPostAdded(blogPost)
                dismiss()
            } else {
                // Handle the case when the input fields are not filled
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
