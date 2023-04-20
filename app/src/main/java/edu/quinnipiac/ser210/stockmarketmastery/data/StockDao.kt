package edu.quinnipiac.ser210.stockmarketmastery.data

import androidx.lifecycle.LiveData
import androidx.room.*
import edu.quinnipiac.ser210.stockmarketmastery.data.Stock
@Dao
interface StockDao {
    @Insert
    suspend fun insert(stock: Stock)

    @Update
    suspend fun update(stock: Stock)

    @Delete
    suspend fun delete(stock: Stock)

    @Query("SELECT * FROM stock_table WHERE id = :id")
    fun get(id: Int): LiveData<Stock>

    @Query("SELECT * FROM stock_table ORDER BY id ASC")
    fun getAll(): LiveData<List<Stock>>
}