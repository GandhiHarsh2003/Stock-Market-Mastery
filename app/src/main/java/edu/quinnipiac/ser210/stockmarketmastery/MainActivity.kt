package edu.quinnipiac.ser210.stockmarketmastery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupActionBarWithNavController(this, navController)
        navController = navHostFragment.navController
        // configuration for tool bar
        val builder = AppBarConfiguration.Builder(navController.graph)
        val appBarConfiguration = builder.build()
        toolbar.setupWithNavController(navController, appBarConfiguration)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.items_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // share logic
            //https://developer.android.com/training/sharing/send and https://stackoverflow.com/questions/50689206/how-i-can-retrieve-current-fragment-in-navhostfragment
            R.id.share -> {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                val stringBuilder = StringBuilder()

                // Get the currently displayed country in the CountryFragment
                val navHostFragment =
                    supportFragmentManager.primaryNavigationFragment as NavHostFragment?
                val stockFragment =
                    navHostFragment?.childFragmentManager?.primaryNavigationFragment as? StockDetailFragment
                if (stockFragment != null) {
//                    val country = countryFragment?.let {countries.getOrNull(it.countryIndex) }

//                    val stock = stockFragment?.let { data }
//
//                    // Append data from the currently displayed country to the string builder
//                    stringBuilder.append("Country: ${country?.country}\n")
//                    stringBuilder.append("Continent: ${country?.continent ?: "N/A"}\n")
//                    stringBuilder.append("Population: ${country?.population}\n")
//                    stringBuilder.append("Total Cases: ${country?.cases?.total}\n")
//                    stringBuilder.append("Critical Cases: ${country?.cases?.critical}\n")
//                    stringBuilder.append("Recovered Cases: ${country?.cases?.recovered}\n")
//                    stringBuilder.append("Total Deaths: ${country?.deaths?.total}\n\n")
                }

                shareIntent.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString())
                startActivity(Intent.createChooser(shareIntent, "Share via"))

                true
            }
            // navigate to other options configured in tool bar
            else -> NavigationUI.onNavDestinationSelected(
                item!!,
                navController
            ) || super.onOptionsItemSelected(item)
        }
    }
}