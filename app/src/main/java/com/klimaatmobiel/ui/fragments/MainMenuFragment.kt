package com.klimaatmobiel.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.projecten3android.R
import com.example.projecten3android.databinding.FragmentMainMenuBinding
import com.google.android.material.snackbar.Snackbar
import com.klimaatmobiel.data.database.ProductsDatabase
import com.klimaatmobiel.data.database.getDatabase
import com.klimaatmobiel.data.network.KlimaatmobielApi
import com.klimaatmobiel.domain.Group
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.domain.enums.KlimaatMobielApiStatus
import com.klimaatmobiel.ui.ViewModelFactories.MainMenuViewModelFactory
import com.klimaatmobiel.ui.viewModels.MainMenuViewModel

/**
 * A simple [Fragment] subclass.
 */
class MainMenuFragment : Fragment() {

    private lateinit var viewModel: MainMenuViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentMainMenuBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val apiService = KlimaatmobielApi.retrofitService

        val viewModelFactory = MainMenuViewModelFactory(KlimaatmobielRepository(apiService, getDatabase(context!!.applicationContext)))

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainMenuViewModel::class.java)

        binding.mainMenuViewModel = viewModel

        viewModel.navigateToAddGroup.observe(this, Observer {
            if(it != null) {
                findNavController().navigate(MainMenuFragmentDirections.actionMainMenuFragment2ToAddGroupFragment3(it))
            }
        })
//        viewModel.navigateToWebshop.observe(this, Observer {
//            if(it != null){
//                findNavController().navigate(MainMenuFragmentDirections.actionMainMenuFragment2ToBottomNavigationWebshopFragment(it))
//                //viewModel.onWebshopNavigated()
//            }
//        })

        viewModel.status.observe(this, Observer {
            when(it) {
                KlimaatMobielApiStatus.ERROR -> {
                    Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        getString(R.string.project_code_error),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })

        return binding.root




    }
}



