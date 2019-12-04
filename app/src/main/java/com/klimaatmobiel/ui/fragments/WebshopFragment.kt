package com.klimaatmobiel.ui.fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager

import com.example.projecten3android.R
import com.example.projecten3android.databinding.FragmentWebshopBinding
import com.google.android.material.snackbar.Snackbar
import com.klimaatmobiel.data.network.KlimaatmobielApi
import com.klimaatmobiel.domain.Group
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.domain.enums.KlimaatMobielApiStatus
import com.klimaatmobiel.ui.ViewModelFactories.WebshopViewModelFactory
import com.klimaatmobiel.ui.adapters.OrderPreviewListAdapter
import com.klimaatmobiel.ui.adapters.ProductListAdapter
import com.klimaatmobiel.ui.viewModels.MainMenuViewModel
import com.klimaatmobiel.ui.viewModels.WebshopViewModel
import kotlinx.android.synthetic.main.fragment_webshop.*
import timber.log.Timber
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class WebshopFragment : Fragment() {


    private lateinit var viewModel: WebshopViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val binding = FragmentWebshopBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel = activity?.run {
            ViewModelProviders.of(this)[WebshopViewModel::class.java]
        } ?: throw Exception("Invalid Activity")


        binding.webshopViewModel = viewModel

        val adapter = ProductListAdapter(ProductListAdapter.OnClickListener {
            product, action ->  viewModel.onProductClicked(product, action)
        })

        viewModel.status.observe(this, Observer {
            when(it) {
                KlimaatMobielApiStatus.ERROR -> {
                    Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        getString(R.string.project_code_error),
                        Snackbar.LENGTH_LONG
                    ).show()
                    viewModel.onErrorShown()
                }
            }
        })

        /**
         * Decide when a list item should span 2 widths
         *
         * itemViewType = 0 -> HEADER
         * itemViewType = 1 -> PRODUCT
         */
        val manager = GridLayoutManager(context, 2)
        manager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if(adapter.getItemViewType(position) == 1) {
                    1
                } else {
                    2
                }
            }

        }

        binding.productsList.layoutManager = manager
        binding.productsList.adapter = adapter

        /**
         *  Populate the [RecyclerView] when the data is received from the back-end
         */
        viewModel.group.observe(viewLifecycleOwner, Observer {
            it?.let {
                val test = it
                adapter.addHeaderAndSubmitList(it.project.products)
            }
        })

        /**
         * Filter the product list
         * Activates for every key-stroke
         */
        binding.filterText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.isNullOrEmpty()){
                    // Resubmit the full list and apply the new filter
//                    adapter.filter.filter(s)
                    viewModel.filterListString(s)
                    adapter.addHeaderAndSubmitList(viewModel.filteredList.value)
                } else {
                    adapter.addHeaderAndSubmitList(viewModel.group.value!!.project.products)
                }
                adapter.notifyDataSetChanged()
            }
        })

        var productList = viewModel.group.value!!.project.products
        val cats = productList.map { prod -> prod.category!!.categoryName }.toSortedSet()
        val catList = listOf<String>("GEEN FILTER") + cats.toList()

        val dropAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, catList)


        binding.positionSpinner.adapter = dropAdapter

        binding.positionSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    viewModel.filterListCategoryName("")
                    adapter.addHeaderAndSubmitList(viewModel.filteredList.value)
                } else {
                    viewModel.filterListCategoryName(parent.getItemAtPosition(position).toString())
                    adapter.addHeaderAndSubmitList(viewModel.filteredList.value)
                }
            }
        }

        return binding.root
    }
}
