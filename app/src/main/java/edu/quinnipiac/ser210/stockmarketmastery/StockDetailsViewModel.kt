package edu.quinnipiac.ser210.stockmarketmastery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.quinnipiac.ser210.stockmarketmastery.data.Stock
import edu.quinnipiac.ser210.stockmarketmastery.data.StockDao
import kotlinx.coroutines.launch

class StockDetailsViewModel(private val stockDao: StockDao): ViewModel() {

    // Cache all items form the database using LiveData.
    val allItems: LiveData<List<Stock>> = stockDao.getAll()

    private fun updateStock(stock: Stock) {
        viewModelScope.launch {
            stockDao.update(stock)
        }
    }
    private fun insertSock(stock: Stock) {
        viewModelScope.launch {
            stockDao.insert(stock)
        }
    }
    fun deleteStock(stock: Stock) {
        viewModelScope.launch {
            stockDao.delete(stock)
        }
    }

    private fun getNewStock(companyName: String, purchased: String): Stock {
        return Stock(
            companyName = companyName,
            purchased = purchased.toDouble()
        )
    }

    private fun getUpdatedStockEntry(
        itemId: Int,
        companyName: String,
        purchased: String
    ): Stock {
        return Stock(
            id = itemId,
            companyName = companyName,
            purchased = purchased.toDouble()
        )
    }
}