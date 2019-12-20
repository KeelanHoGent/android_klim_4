package com.klimaatmobiel.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.projecten3android.R
import com.example.projecten3android.databinding.FragmentMainMenuBinding
import com.google.android.material.snackbar.Snackbar
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.domain.enums.KlimaatMobielApiStatus
import com.klimaatmobiel.ui.viewModels.MainMenuViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class MainMenuFragment : Fragment() {

    private val viewModel: MainMenuViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentMainMenuBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.mainMenuViewModel = viewModel

        viewModel.navigateToAddGroup.observe(this, Observer {
            if(it != null) {
                findNavController().navigate(MainMenuFragmentDirections.actionMainMenuFragment2ToAddGroupFragment3(it))
            }
            viewModel.onAddGroupNavigated()
        })

        viewModel.status.observe(this, Observer {
            when(it) {
                KlimaatMobielApiStatus.ERROR -> {
                    Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        viewModel.customErrorMessage,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })

        return binding.root




    }
}



