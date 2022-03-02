package net.jahez.jahezchallenge.UI.activity

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import net.jahez.jahezchallenge.BaseActivity
import net.jahez.jahezchallenge.R
import net.jahez.jahezchallenge.UI.adapter.RestaurantAdapter
import net.jahez.jahezchallenge.databinding.ActivityMainBinding
import net.jahez.jahezchallenge.Network.MainRepository
import net.jahez.jahezchallenge.Network.RetrofitService
import net.jahez.jahezchallenge.Utils.LocaleUtil
import net.jahez.jahezchallenge.ViewModel.MainViewModel
import net.jahez.jahezchallenge.ViewModel.MainViewModelFactory
import java.io.IOException
import java.nio.charset.Charset


class MainActivity : BaseActivity() {
    // view binding for the activity
    private lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()
    val adapter = RestaurantAdapter()
    val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)

        }
        binding.rvList.layoutManager = layoutManager
        viewModel = ViewModelProvider(this, MainViewModelFactory(MainRepository(retrofitService))).get(MainViewModel::class.java)
        binding.rvList.adapter = adapter
        viewModel.restaurantsList.observe(this , androidx.lifecycle.Observer {
            Log.d(TAG, "onCreate: $it")
            adapter.setRestaurantList(it)
        })

        viewModel.errorMessage.observe(this,androidx.lifecycle.Observer {
        })
        viewModel.getAllRestaurants()



        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            binding.drawerLayout.closeDrawers()

            // Handle navigation view item clicks here.
            when (menuItem.itemId) {

                R.id.nav_language -> {
                    if (storage.getPreferredLocale()=="en")
                    updateAppLocale("ar")
                    else
                        updateAppLocale("en")
                   recreate()
                    //Toast.makeText(this, "language", Toast.LENGTH_LONG).show()

                }

            }
            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here

            true
        }

        binding.ivFilter.setOnClickListener {getSortDialog(this)}

    }



    //appbar - toolbar button click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }




   private fun getSortDialog(context : Context){
       val builder = AlertDialog.Builder(context)
       builder.setTitle("Sort By")

// add a list
       val animals = arrayOf("Distance")
       builder.setItems(animals) { dialog, which ->
           when (which) {
               0 -> {


                   val sortedByDistance = viewModel.restaurantsList.value?.sortedBy { it.distance }
                   adapter.setRestaurantList(sortedByDistance)


               }
           }
       }

// create and show the alert dialog
       val dialog = builder.create()
       dialog.show()
   }
    private fun updateAppLocale(locale: String) {
        storage.setPreferredLocale(locale)
        LocaleUtil.applyLocalizedContext(applicationContext, locale)
    }
}