package com.klimaatmobiel.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.projecten3android.databinding.FragmentAddGroupBinding
import com.google.android.material.snackbar.Snackbar
import com.klimaatmobiel.data.database.getDatabase
import com.klimaatmobiel.data.network.KlimaatmobielApi
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.domain.Pupil
import com.klimaatmobiel.domain.enums.KlimaatMobielApiStatus
import com.klimaatmobiel.ui.adapters.AddGroupListAdapter
import com.klimaatmobiel.ui.viewModels.AddGroupViewModel
import org.koin.android.viewmodel.ext.android.getSharedViewModel

/**
 * A simple [Fragment] subclass.
 */
class AddGroupFragment : Fragment() {

    private lateinit var viewModel: AddGroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = getSharedViewModel()



        val binding = FragmentAddGroupBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val adapter = AddGroupListAdapter()

        binding.recyclerGroupmembers.adapter = adapter




        viewModel.group.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it.pupils)
            }
        })

        viewModel.navigateToWebshop.observe(this, Observer {
            if(it != null) {
                findNavController().navigate(AddGroupFragmentDirections.actionAddGroupFragment3ToBottomNavigationWebshopFragment(it))
            }
        })

        binding.addGroupViewModel = viewModel

        binding.buttonAddPupil.setOnClickListener {

            try {
                viewModel.onClickedAddPupil(binding.editTextAddPupil.text.toString(), binding.editTextAddPupilName.text.toString())
                binding.editTextAddPupil.setText("")
                binding.editTextAddPupilName.setText("")
                adapter.notifyDataSetChanged()
            }catch(e: Exception) {
                Snackbar.make(
                    activity!!.findViewById(android.R.id.content),
                    "naam invullen",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }


      /*
        binding.buttonGroupAdded.setOnClickListener{
            viewModel.onclickedNext()
        }

        viewModel.status.observe(this, Observer {
            when(it) {
                KlimaatMobielApiStatus.ERROR -> {
                    Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        getString(R.string.error_connection),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        })
        */


        return binding.root
    }


}
