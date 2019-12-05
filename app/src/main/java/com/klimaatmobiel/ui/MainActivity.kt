package com.klimaatmobiel.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.projecten3android.R
import com.example.projecten3android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //setContentView(R.layout.activity_main)

    }

    fun setToolbarTitle(titleString : String){
        supportActionBar!!.title = titleString
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }



    /**
     * Decide if the activity should relaunch (logout current user and reset the app) or to navigate the backstack.
     */
    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.myNavHostFragment)!!.childFragmentManager.backStackEntryCount
        if(fragment == 1) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            applicationContext.startActivity(intent)
        } else {
            super.onBackPressed()
        }
    }
}
