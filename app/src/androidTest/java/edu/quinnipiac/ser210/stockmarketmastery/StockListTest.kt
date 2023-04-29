package edu.quinnipiac.ser210.stockmarketmastery

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import edu.quinnipiac.ser210.stockmarketmastery.util.EspressoIdlingResource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Stock test class that takes care of the UI testing that will be implemented for app
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/29/23
 * NOTE: Espresso resource code from lesson 11 was used
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class StockListTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    val STOCK_IN_TEST = "TSLA" // Tesla's great

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
        EspressoIdlingResource.increment()
        // 3 second network request
        val job = GlobalScope.launch {
            delay(3000)
        }
        job.invokeOnCompletion {
            // our network call ended!
            EspressoIdlingResource.decrement()
        }
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    // will check to see if the splash screen will appear when application starts
    @Test
    fun test_isSplashScreen_onAppLaunch() {
        onView(withId(R.id.start)).check(matches(isDisplayed()))
    }

    // will check to see if the button in splash screen will navigate user to the Search stock fragment
    @Test
    fun test_SplashScreen_toSearchStockFragment() {
        onView(withId(R.id.start)).perform(click())
    }

    // will type in tesla's stock symbol and navigate user to next screen
    @Test
    fun test_backNavigation_toStockDetailsFragment() {
        // call the splash screen to search stock fragment test
        test_SplashScreen_toSearchStockFragment()

        // input the stock companies name
        onView(withId(R.id.SymbolInput)).perform(typeText(STOCK_IN_TEST))
        // once entered, move to the stock details fragment
        onView(withId(R.id.SearchButton)).perform(click())

        // this might work, it goes too fast during testing
        onView(withId(R.id.stockRecyclerView)).perform(swipeDown())

        pressBack()

    }

}