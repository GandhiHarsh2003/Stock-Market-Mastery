package edu.quinnipiac.ser210.stockmarketmastery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.quinnipiac.ser210.stockmarketmastery.data.StockDao

class StockDetailsViewModelFactory(private val dao: StockDao): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StockDetailsViewModel::class.java)){
            return  StockDetailsViewModel(dao) as T
        }
        throw  java.lang.IllegalArgumentException("Unknown ViewModel")
    }
}