package edu.quinnipiac.ser210.stockmarketmastery

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import edu.quinnipiac.ser210.stockmarketmastery.data.Stock
import edu.quinnipiac.ser210.stockmarketmastery.data.StockDao
import edu.quinnipiac.ser210.stockmarketmastery.databinding.FragmentStockDetailBinding
import kotlinx.coroutines.launch
/**
 * StockDetailsViewModel to get reference of the bought stocks and be able to add,update,delete, etc (NOT FROM SPRINT ONE)
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/20/23
 */
class StockDetailsViewModel(private val stockDao: StockDao): ViewModel() {

    val allItems: LiveData<List<Stock>> = stockDao.getAll().asLiveData()
    private var _binding: FragmentStockDetailBinding? = null
    private val binding get() = _binding!!


    fun isStockAvailable(stock: Stock): Boolean {
        return (stock.stockQuantity > 0)
    }

    fun updateItem(itemId: Int, companyName: String, purchased: String, stockQuantity: String) {
        val newItem = getUpdatedStockEntry(itemId, companyName, purchased, stockQuantity)
        updateItem(newItem)
    }

    private fun updateItem(stock: Stock) {
        viewModelScope.launch {
            stockDao.update(stock)
        }
    }

    fun sellItem(context: Context, stock: Stock, sellPrice: String, boughtPrice: String, quant: String) {
        val bp: Double = boughtPrice.toDouble()
        println(bp)
        val sp: Double = sellPrice.toDouble()
        println(sp)

        val quantity: Int = quant.toInt()
        println(quantity)


        if (stock.stockQuantity >= quantity) {
            // Decrease the quantity by 1
            val profitOrNot: Double = sp * quantity
            println(profitOrNot)

            val calculate: Double = profitOrNot - (bp * quantity)
            println(calculate)

            val newItem = stock.copy(stockQuantity = (stock.stockQuantity - quantity))
            Toast.makeText(context, "Profit made $calculate", Toast.LENGTH_SHORT).show()
            updateItem(newItem)
        }
    }


    fun addNewItem(companyName: String, purchased: String, stockQuantity: String) {
        val newItem = getNewStock(companyName, purchased, stockQuantity)
        insetItem(newItem)
    }



    private fun insetItem(stock: Stock) {
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

    fun retrieveItem(id: Int): LiveData<Stock> {
        return stockDao.get(id).asLiveData()
    }

    fun isEntryValid(stockQuantity: String): Boolean {
        if (stockQuantity.isBlank()) {
            return false
        }
        return true
    }

    private fun getNewStock(companyName: String, purchased: String, stockQuantity: String): Stock {
        return Stock(
            companyName = companyName,
            purchased = purchased.toDouble(),
            stockQuantity = stockQuantity.toInt(),
        )
    }

    private fun getUpdatedStockEntry(
        itemId: Int,
        companyName: String,
        purchased: String,
        stockQuantity: String,
    ): Stock {
        return Stock(
            id = itemId,
            companyName = companyName,
            purchased = purchased.toDouble(),
            stockQuantity = stockQuantity.toInt()
        )
    }
}