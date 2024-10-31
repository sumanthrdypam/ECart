package com.sam.ecartapp.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.sam.ecartapp.AppConstants
import com.sam.ecartapp.R
import com.sam.ecartapp.SharedPreferenceManager
import com.sam.ecartapp.databinding.ActivityMainBinding
import com.sam.ecartapp.databinding.HeaderLayoutBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
         navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.navView, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            Toast.makeText(this@MainActivity,destination.toString(),Toast.LENGTH_SHORT).show()
            when (destination.id) {
//                R.id.onboardingFragment   -> {
//                    binding.root.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
//                }
                R.id.loginFragment ->{
                    binding.root.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.registerUserFragment ->{
                    binding.root.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }

                else -> {
                    binding.root.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
            }
        }
        handleNavigationItemSelected()
    }

    private fun handleNavigationItemSelected() {
        setUserDetails()
        binding.navView.setNavigationItemSelectedListener {
            menuItem->
            if(menuItem.itemId == R.id.itemLogout){
                Toast.makeText(this@MainActivity,"Logout Successful",Toast.LENGTH_SHORT).show()
                SharedPreferenceManager.clearUser()
                SharedPreferenceManager.saveBoolean(AppConstants.IS_LOGIN,false)
                finish()
            }else if(menuItem.itemId == R.id.itemCart){
                navController.navigate(R.id.cartFragment)
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }else if(menuItem.itemId == R.id.itemHome){
                navController.navigate(R.id.homeScreen)
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }else if(menuItem.itemId == R.id.itemOrders){
                navController.navigate(R.id.ordersFragment)
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            true
        }
    }

    private fun setUserDetails(){
        val user = SharedPreferenceManager.getUser()
        val headerView = binding.navView.getHeaderView(0)
        val headerBinding = HeaderLayoutBinding.bind(headerView)
        headerBinding.userMail.text = user.emailId
        headerBinding.userName.text = user.fullName
        headerBinding.userMobileNumber.text = user.mobileNumber
    }

    fun toggleDrawer(){
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }


}