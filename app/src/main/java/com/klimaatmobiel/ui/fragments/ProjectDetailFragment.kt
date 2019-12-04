package com.klimaatmobiel.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.projecten3android.databinding.FragmentProjectDetailBinding
import com.klimaatmobiel.data.database.getDatabase
import com.klimaatmobiel.data.network.KlimaatmobielApi
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.ui.MainActivity
import com.klimaatmobiel.ui.ViewModelFactories.ProjectDetailViewModelFactory
import com.klimaatmobiel.ui.viewModels.ProjectDetailViewModel

class ProjectDetailFragment : Fragment() {

    private lateinit var viewModel: ProjectDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentProjectDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        (activity as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val apiService = KlimaatmobielApi.retrofitService
        val viewModelFactory = ProjectDetailViewModelFactory(KlimaatmobielRepository(apiService, getDatabase(context!!.applicationContext)), ProductDetailFragmentArgs.fromBundle(requireArguments()).projectId )
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProjectDetailViewModel::class.java)

        binding.projectDetailViewModel = viewModel

        viewModel.project.observe(viewLifecycleOwner, Observer {
            binding.project = it
        })

        return binding.root
    }
}