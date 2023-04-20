package edu.quinnipiac.ser210.stockmarketmastery

import android.app.Application
import edu.quinnipiac.ser210.stockmarketmastery.data.StockDatabase
/**
 * StockApplication responsible for initializing database (NOT FROM SPRINT ONE)
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/20/23
 */
class StockApplication : Application() {
    // create instance of database
    val database: StockDatabase by lazy { StockDatabase.getDatabase(this) }
}
