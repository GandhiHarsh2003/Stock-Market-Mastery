package edu.quinnipiac.ser210.stockmarketmastery

import android.app.Application
import edu.quinnipiac.ser210.stockmarketmastery.data.StockDatabase

class StockApplication : Application() {
    // Using by lazy so the database is only created when needed
    // rather than when the application starts
    val database: StockDatabase by lazy { StockDatabase.getDatabase(this) }
}
