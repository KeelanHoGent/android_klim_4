package com.klimaatmobiel.ui.fragments


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager

import com.example.projecten3android.R
import com.example.projecten3android.databinding.FragmentWebshopBinding
import com.google.android.material.snackbar.Snackbar
import com.klimaatmobiel.domain.enums.KlimaatMobielApiStatus
import com.klimaatmobiel.domain.enums.SortStatus
import com.klimaatmobiel.ui.adapters.ProductListAdapter
import com.klimaatmobiel.ui.viewModels.WebshopViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * A simple [Fragment] subclass.
 */
class WebshopFragment : Fragment() {



    private val viewModel: WebshopViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val binding = FragmentWebshopBinding.inflate(inflater)
        binding.lifecycleOwner = this



        binding.webshopViewModel = viewModel

        val adapter = ProductListAdapter(ProductListAdapter.OnClickListener {
            product, action ->
            run {

                viewModel.onProductClicked(product, action)
                if(action == 0){
                    Snackbar.make(
                        activity!!.findViewById(android.R.id.content),
                        "Product toegevoegd",
                        Snackbar.LENGTH_SHORT
                    ).show()

                    // werkt nog niet
                    var anim = AnimationUtils.loadAnimation(context, R.anim.enlarge)
                    var img = activity!!.findViewById<ImageView>(R.id.add_to_cart_image)
                    img.startAnimation(anim)
                }



            }
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
                    viewModel.filterListString(adapter, s)
                } else {
                    viewModel.filterListString(adapter, "")
                }
                adapter.notifyDataSetChanged()
            }
        })

        /**
         * This fills the spinner to select a specific category
         * When an option is selected it will filter the list to only show the selected category
         */

        val productList = viewModel.group.value!!.project.products
        val cats = productList.map { prod -> prod.category!!.categoryName }.toSortedSet()
        val catList = listOf("GEEN FILTER") + cats.toList()

        val dropAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, catList)


        binding.categorieSpinner.adapter = dropAdapter

        binding.categorieSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position == 0) {
                    viewModel.filterListCategoryName(adapter, "")
                } else {
                    viewModel.filterListCategoryName(adapter, parent.getItemAtPosition(position).toString())
                }
            }
        }

        val arrayOfSortStatus = SortStatus.values()

        val sortAdapter = ArrayAdapter(
            context!!,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOfSortStatus
        )

        binding.sorteerSpinner.adapter = sortAdapter

        binding.sorteerSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.sortList(
                    adapter,
                    SortStatus.valueOf(parent.getItemAtPosition(position).toString())
                )
            }

        }

        return binding.root
    }
}
