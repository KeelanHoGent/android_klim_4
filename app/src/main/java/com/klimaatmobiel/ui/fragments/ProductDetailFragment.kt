package com.klimaatmobiel.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.projecten3android.databinding.FragmentProductDetailBinding
import com.klimaatmobiel.ui.MainActivity
import com.klimaatmobiel.ui.viewModels.ProductDetailViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

/**
 * Fragment that displays information about a specific product retrieved from the API.
 *
 * @author Robbe Decorte
 */
class ProductDetailFragment : Fragment() {

    private val productDetailViewModel: ProductDetailViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentProductDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        (activity as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Inject apiService and database into the ViewModel and request it

        binding.productDetailViewModel = productDetailViewModel

        productDetailViewModel.product.observe(viewLifecycleOwner, Observer {
            binding.product = it
        })

        return binding.root
    }
}
