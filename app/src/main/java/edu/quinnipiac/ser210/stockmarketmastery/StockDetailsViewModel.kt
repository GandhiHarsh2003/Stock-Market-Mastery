package edu.quinnipiac.ser210.stockmarketmastery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.quinnipiac.ser210.stockmarketmastery.data.Stock
import edu.quinnipiac.ser210.stockmarketmastery.data.StockDao
import kotlinx.coroutines.launch
/**
 * StockDetailsViewModel to get reference of the bought stocks and be able to add,update,delete, etc (NOT FROM SPRINT ONE)
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/20/23
 */
class StockDetailsViewModel(private val stockDao: StockDao): ViewModel() {

    val allItems: LiveData<List<Stock>> = stockDao.getAll()

    private fun updateStock(stock: Stock) {
        viewModelScope.launch {
            stockDao.update(stock)
        }
    }
    private fun addStock(stock: Stock) {
        viewModelScope.launch {
            stockDao.insert(stock)
        }
    }
    // maybe we'll get to this in the third sprint
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