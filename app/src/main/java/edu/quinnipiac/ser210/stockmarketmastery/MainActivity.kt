package edu.quinnipiac.ser210.stockmarketmastery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

/**
 * Main Activity responsible for containing nav host and tool bar configurations (at some point the navigation drawer will get added)
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/20/23
 */
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        // configuration for tool bar
        val builder = AppBarConfiguration.Builder(navController.graph)
        builder.setOpenableLayout(drawer)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        NavigationUI.setupWithNavController(navView, navController)
        val appBarConfiguration = builder.build()
        toolbar.setupWithNavController(navController, appBarConfiguration)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.items_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item) || when (item.itemId) {
            // share logic (reused code)
            //https://developer.android.com/training/sharing/send and https://stackoverflow.com/questions/50689206/how-i-can-retrieve-current-fragment-in-navhostfragment
            R.id.share -> {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                val stringBuilder = StringBuilder()

                // Get the currently displayed stock in the stock detail fragment
                val navHostFragment =
                    supportFragmentManager.primaryNavigationFragment as NavHostFragment?
                val stockFragment =
                    navHostFragment?.childFragmentManager?.primaryNavigationFragment as? StockDetailFragment
                if (stockFragment != null) {
                    val stock = stockFragment?.let { data.getOrNull(it.stockIndex) }
                    stringBuilder.append("Company Name: ${stock?.quoteStock?.name}\n")
                    stringBuilder.append("Current price: ${stock?.realTimePrice?.price}\n")
                    stringBuilder.append("Average Volume:  ${stock?.quoteStock?.volume}\n")
                    stringBuilder.append("Stock High:  ${stock?.quoteStock?.high}\n")
                    stringBuilder.append("Stock Low:  ${stock?.quoteStock?.low}\n")
                    stringBuilder.append("Stock Change:  ${stock?.quoteStock?.change}\n")
                    stringBuilder.append("Stock Close:  ${stock?.quoteStock?.close}\n")
                    stringBuilder.append("Stock Currency:  ${stock?.quoteStock?.currency}\n")
                    stringBuilder.append("Stock Date-Time:  ${stock?.quoteStock?.datetime}\n")
                    stringBuilder.append("Stock Market Open:  ${stock?.quoteStock?.is_market_open}\n")
                    stringBuilder.append("Stock MIC Code:  ${stock?.quoteStock?.mic_code}\n")
                    stringBuilder.append("Stock Percent Change:  ${stock?.quoteStock?.percent_change}\n")
                    stringBuilder.append("Stock Previous Close:  ${stock?.quoteStock?.previous_close}\n")
                    stringBuilder.append("Stock Symbol:  ${stock?.quoteStock?.symbol}\n")
                    stringBuilder.append("Stock Time Stamp:  ${stock?.quoteStock?.close}\n")

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