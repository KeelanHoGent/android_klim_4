package com.klimaatmobiel.ui.fragments


import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.example.projecten3android.R
import com.klimaatmobiel.ui.viewModels.WebshopViewModel
import com.example.projecten3android.databinding.FragmentBottomNavigationWebshopBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.klimaatmobiel.ui.MainActivity
import android.view.LayoutInflater
import com.google.android.material.badge.BadgeDrawable
import timber.log.Timber
import org.koin.android.viewmodel.ext.android.sharedViewModel


/**
 * A simple [Fragment] subclass.
 */
class BottomNavigationWebshopFragment : Fragment() {
    private val viewModel: WebshopViewModel by sharedViewModel()

    private var currentFragment: Fragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        val binding = FragmentBottomNavigationWebshopBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val group = BottomNavigationWebshopFragmentArgs.fromBundle(arguments!!).group

        (activity as MainActivity).setToolbarTitle("Klimaatmobiel" + " - " + group.groupName + " - " + group.project.projectName)
        (activity as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

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
        binding.bottomNavigationWebshop

        viewModel.aantalItemsInOrder.observe(this, Observer {
            if(it != null){

                updateBadge(binding.bottomNavigationWebshop)
            }
        })


        return binding.root
    }
    fun updateBadge(bottomNavigationWebshop: BottomNavigationView){
        var aantal = viewModel.getAantalItemsOrder()
        val badge : BadgeDrawable = bottomNavigationWebshop.getOrCreateBadge(R.id.nav_order)!!.apply { number = aantal}
        

    }

    fun triggerWebshopBottomNavigation(menuItem : MenuItem) {
        var fragment : Fragment = WebshopFragment()
        if(currentFragment == null) currentFragment = fragment
        val ft = (activity as MainActivity).supportFragmentManager.beginTransaction()

        when(menuItem.itemId){

            R.id.nav_order -> {
                fragment = ShoppingCartFragment()
                ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left)
            }
            R.id.nav_webshop -> {
                fragment = WebshopFragment()
                if(currentFragment is ShoppingCartFragment)
                    ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_left, R.anim.slide_out_right)
                else if(currentFragment is ProjectDetailFragment)
                    ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left)

            }
            R.id.nav_info -> {
                fragment = ProjectDetailFragment()
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right, R.anim.slide_in_left, R.anim.slide_out_right)

            }

        }
        currentFragment = fragment;
        ft.replace(R.id.bottom_navigation_container, fragment).commit()

    }

}
