package edu.quinnipiac.ser210.stockmarketmastery.util

import androidx.test.espresso.idling.CountingIdlingResource

/**
 * Idling Resource that's responsible to serve as a resource for the UI test
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/29/23
 * NOTE: Espresso resource code from lesson 11 was used
 */
object EspressoIdlingResource {

    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }

}