package edu.quinnipiac.ser210.stockmarketmastery

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
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


    // checks to see if there's stock saved in the database
    fun isStockAvailable(stock: Stock): Boolean {
        return (stock.stockQuantity > 0)
    }

    // will allow the ability to update a stock saved in the database
    fun updateItem(itemId: Int, companyName: String, purchased: String, stockQuantity: String) {
        val newItem = getUpdatedStockEntry(itemId, companyName, purchased, stockQuantity)
        updateItem(newItem)
    }

    // update that stock
    private fun updateItem(stock: Stock) {
        viewModelScope.launch {
            stockDao.update(stock)
        }
    }

    // will get the stock saved and perform some calcualtions to determine if profit was earned or not
    fun sellItem(context: Context, stock: Stock, sellPrice: String, boughtPrice: String, quant: String): Float {
        val bp: Double = boughtPrice.toDouble()
        println(bp)
        val sp: Double = sellPrice.toDouble()
        println(sp)

        val quantity: Int = quant.toInt()
        println(quantity)

        var calculate: Float = 0.0F


        // math conversion to check the amount of profit earned on specific stock
        if (stock.stockQuantity >= quantity) {
            // Decrease the quantity by 1
            val profitOrNot: Double = sp * quantity
            println(profitOrNot)

            calculate = (profitOrNot - (bp * quantity)).toFloat()
            println(calculate)

            val newItem = stock.copy(stockQuantity = (stock.stockQuantity - quantity))
            Toast.makeText(context, "Profit made $calculate", Toast.LENGTH_SHORT).show()
            updateItem(newItem)
        }
        println("R u sure $calculate")
        return calculate
    }


    // insert stock item to the database
    fun addNewItem(companyName: String, purchased: String, stockQuantity: String) {
        val newItem = getNewStock(companyName, purchased, stockQuantity)
        insetItem(newItem)
    }


    // insert stock item to the database
    private fun insetItem(stock: Stock) {
        viewModelScope.launch {
            stockDao.insert(stock)
        }
    }
    // deletes the saved stock
    fun deleteStock(stock: Stock) {
        viewModelScope.launch {
            stockDao.delete(stock)
        }
    }

    // find the specific stock saved in the database
    fun retrieveItem(id: Int): LiveData<Stock> {
        return stockDao.get(id).asLiveData()
    }

    // will check if input is valid to save in the database
    fun isEntryValid(stockQuantity: String): Boolean {
        if (stockQuantity.isBlank()) {
            return false
        }
        return true
    }

    // get the new stock
    private fun getNewStock(companyName: String, purchased: String, stockQuantity: String): Stock {
        return Stock(
            companyName = companyName,
            purchased = purchased.toDouble(),
            stockQuantity = stockQuantity.toInt(),
        )
    }

    // get the update stock if the user updates data saved
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