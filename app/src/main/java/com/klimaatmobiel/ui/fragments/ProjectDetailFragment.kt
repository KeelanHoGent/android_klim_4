package com.klimaatmobiel.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.projecten3android.databinding.FragmentProjectDetailBinding
import com.klimaatmobiel.ui.viewModels.WebshopViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class ProjectDetailFragment : Fragment() {

    private val viewModel: WebshopViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentProjectDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this




        binding.webshopViewModel = viewModel

        viewModel.project.observe(viewLifecycleOwner, Observer {
            binding.project = it
        })

        return binding.root
    }
}