package edu.quinnipiac.ser210.stockmarketmastery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.quinnipiac.ser210.stockmarketmastery.data.StockDao
/**
 * StockDetailsViewModelFactory factory class in conjunction with the StockDetails view model (NOT FROM SPRINT ONE)
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/20/23
 */
class StockDetailsViewModelFactory(private val dao: StockDao): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StockDetailsViewModel::class.java)){
            return  StockDetailsViewModel(dao) as T
        }
        throw  java.lang.IllegalArgumentException("Unknown ViewModel")
    }
}