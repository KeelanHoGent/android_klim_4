package com.klimaatmobiel.ui.fragments


import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.example.projecten3android.R
import com.klimaatmobiel.ui.ViewModelFactories.WebshopViewModelFactory
import com.klimaatmobiel.ui.viewModels.WebshopViewModel
import com.example.projecten3android.databinding.FragmentBottomNavigationWebshopBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.klimaatmobiel.PusherApplication
import com.klimaatmobiel.data.database.getDatabase
import com.klimaatmobiel.data.network.KlimaatmobielApi
import com.klimaatmobiel.domain.KlimaatmobielRepository
import com.klimaatmobiel.ui.MainActivity
import android.widget.TextView
import androidx.core.view.MenuItemCompat
import androidx.core.view.isVisible
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class BottomNavigationWebshopFragment : Fragment() {

    private lateinit var viewModel: WebshopViewModel
    private lateinit var textCartItemCount: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val binding = FragmentBottomNavigationWebshopBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val group = BottomNavigationWebshopFragmentArgs.fromBundle(arguments!!).group

        (activity as MainActivity).setToolbarTitle("Klimaatmobiel" + " - " + group.groupName + " - " + group.project.projectName)
        (activity as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)



        val apiService = KlimaatmobielApi.retrofitService

        viewModel = activity?.run {
            ViewModelProviders.of(this, WebshopViewModelFactory(group, KlimaatmobielRepository(apiService, getDatabase(context!!.applicationContext))))[WebshopViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        binding.viewModel = viewModel



        binding.bottomNavigationWebshop.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
            triggerWebshopBottomNavigation(it)
            true
        })

        //standaard navigatie als app worst opgestart voor de eerste keer
        if(savedInstanceState == null){
            binding.bottomNavigationWebshop.selectedItemId = R.id.nav_webshop
        }

        // Navigate to the product detail fragment
        viewModel.navigateToProductDetail.observe(this, Observer {
            if(it != null) {
                findNavController().navigate(
                    BottomNavigationWebshopFragmentDirections.actionBottomNavigationWebshopFragmentToProductDetailFragment2(
                        viewModel.navigateToProductDetail.value!![0], // ProjectId
                        viewModel.navigateToProductDetail.value!![1]  // ProductId
                    )
                )
                viewModel.onProductDetailNavigated()
            }
        })

        //initialiseren van badge

        val menu = binding.bottomNavigationWebshop.menu
        val item = menu.findItem(R.id.nav_order)
        val searchView = item.getActionView() as FrameLayout

        textCartItemCount = searchView.findViewById<TextView>(R.id.cart_badge)

        viewModel.aantalNieuweItems.observe(this, Observer {
            if(it != null){
                showBadge()
            }
        })

        return binding.root
    }

    fun showBadge(){
        if(viewModel.aantalNieuweItems != null){
            Timber.i("ja???????????????????????????????????????")
            textCartItemCount.setText(viewModel.aantalNieuweItems.value!!.toString())
            if(!textCartItemCount.isVisible){
                Timber.i("probeeer")
                textCartItemCount.visibility = View.VISIBLE
            }
        }else{
            if(textCartItemCount.isVisible){
                textCartItemCount.visibility = View.GONE
            }
        }
    }

    fun triggerWebshopBottomNavigation(menuItem : MenuItem) {
        var fragment : Fragment = WebshopFragment()
        when(menuItem.itemId){

            R.id.nav_order -> {
                fragment = ShoppingCartFragment()
            }
            R.id.nav_webshop -> {
                fragment = WebshopFragment()
            }
            R.id.nav_info -> {
                fragment = ProjectDetailFragment()

            }

        }
        (activity as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.bottom_navigation_container, fragment).commit()

    }

}
