package net.jahez.jahezchallenge.activity

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import net.jahez.jahezchallenge.BaseActivity
import net.jahez.jahezchallenge.R
import net.jahez.jahezchallenge.adapter.RestaurantAdapter
import net.jahez.jahezchallenge.databinding.ActivityMainBinding
import net.jahez.jahezchallenge.model.RestaurantModel
import net.jahez.jahezchallenge.utils.LocaleUtil
import java.io.IOException
import java.nio.charset.Charset
import java.util.*


class MainActivity : BaseActivity() {
    // view binding for the activity
    private lateinit var binding: ActivityMainBinding
    private lateinit var  restaurantsList : List<RestaurantModel>
    private lateinit var rvAdapter: RestaurantAdapter

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
        val restaurantsString = loadJSONFromAsset()
         restaurantsList =
            Gson().fromJson(restaurantsString, Array<RestaurantModel>::class.java).asList()
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

         rvAdapter = RestaurantAdapter(restaurantsList)
         binding.rvList.layoutManager = layoutManager
        binding.rvList.adapter = rvAdapter


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


    private fun loadJSONFromAsset(): String {
        val json: String?
        try {
            val inputStream = assets.open("restaurants.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            val charset: Charset = Charsets.UTF_8
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)
        }
        catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }

   private fun getSortDialog(context : Context){
       val builder = AlertDialog.Builder(context)
       builder.setTitle("Sort By")

// add a list
       val animals = arrayOf("Distance")
       builder.setItems(animals) { dialog, which ->
           when (which) {
               0 -> {


                   val sortedByDistance = restaurantsList.sortedBy { it.distance }
                   rvAdapter.restaurantList=sortedByDistance
                   rvAdapter.notifyDataSetChanged()

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