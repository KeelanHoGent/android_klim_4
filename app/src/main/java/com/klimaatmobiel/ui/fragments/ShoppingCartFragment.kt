package com.klimaatmobiel.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.projecten3android.R
import com.example.projecten3android.databinding.FragmentShoppingCartBinding
import com.google.android.material.snackbar.Snackbar
import com.klimaatmobiel.domain.enums.KlimaatMobielApiStatus
import com.klimaatmobiel.ui.adapters.OrderPreviewListAdapter
import com.klimaatmobiel.ui.viewModels.WebshopViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class ShoppingCartFragment : Fragment() {


    private val viewModel: WebshopViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = FragmentShoppingCartBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        binding.webshopViewModel = viewModel

        binding.orderPreviewList.adapter = OrderPreviewListAdapter(OrderPreviewListAdapter.OnClickListener{ // add
            viewModel.changeOrderItemAmount(it, true)
        }, OrderPreviewListAdapter.OnClickListener{// minus
            viewModel.changeOrderItemAmount(it, false)
        }, OrderPreviewListAdapter.OnClickListener{// delete
            viewModel.removeOrderItem(it)
        })

        viewModel.group.observe(this, Observer{
            if(viewModel.posToRefreshInOrderPreviewListItem != -1){
                binding.orderPreviewList.adapter?.notifyItemChanged(viewModel.posToRefreshInOrderPreviewListItem)
            }
        })

        viewModel.status.observe(this, Observer {
            when(it) {
                KlimaatMobielApiStatus.ERROR -> {
                    Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        getString(R.string.error_connection),
                        Snackbar.LENGTH_LONG
                    ).show()
                    viewModel.onErrorShown()
                }
            }
        })

        return binding.root


    }


}