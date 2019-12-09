package com.klimaatmobiel.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.projecten3android.databinding.FragmentProjectDetailBinding
import com.klimaatmobiel.data.network.KlimaatmobielApi
import com.klimaatmobiel.ui.viewModels.WebshopViewModel

class ProjectDetailFragment : Fragment() {

    private lateinit var viewModel: WebshopViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentProjectDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel = activity?.run {
            ViewModelProviders.of(this)[WebshopViewModel::class.java]
        } ?: throw Exception("Invalid Activity")


        val apiService = KlimaatmobielApi.retrofitService


        binding.webshopViewModel = viewModel

        viewModel.project.observe(viewLifecycleOwner, Observer {
            binding.project = it
        })

        return binding.root
    }
}