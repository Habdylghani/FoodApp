package com.example.foodapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapp.databinding.FragmentBlogBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class BlogFragment : Fragment(), AddBlogPostDialogFragment.AddBlogPostListener {
    private val PREFS_NAME = "BlogPrefs"
    private val blogList = mutableListOf<BlogPost>()
    private lateinit var adapter: BlogAdapter

    private var _binding: FragmentBlogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBlogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BlogAdapter(blogList) { /* No need to handle item click here */ }
        binding.blogRecyclerView.adapter = adapter
        binding.blogRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.fabAddBlogPost.setOnClickListener {
            showAddBlogPostDialog()
        }

        loadBlogData()
    }

    private fun showAddBlogPostDialog() {
        val dialog = AddBlogPostDialogFragment()
        dialog.addBlogPostListener = this
        dialog.show(childFragmentManager, "AddBlogPostDialogFragment")
    }

    override fun onBlogPostAdded(blogPost: BlogPost) {
        blogList.add(blogPost)
        adapter.notifyDataSetChanged()
        saveBlogData()
    }

    private fun saveBlogData() {
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(blogList)
        editor.putString("blogList", json)
        editor.apply()
    }

    private fun loadBlogData() {
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString("blogList", null)
        val type: Type = object : TypeToken<MutableList<BlogPost>>() {}.type

        if (!json.isNullOrBlank()) {
            val loadedBlogList: MutableList<BlogPost> = gson.fromJson(json, type)
            blogList.clear()
            blogList.addAll(loadedBlogList)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
