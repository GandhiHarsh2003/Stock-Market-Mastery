package edu.quinnipiac.ser210.stockmarketmastery.data

import androidx.lifecycle.LiveData
import androidx.room.*
import edu.quinnipiac.ser210.stockmarketmastery.data.Stock
import kotlinx.coroutines.flow.Flow

/**
 * StockDao for providing access to database (NOT FROM SPRINT ONE)
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/20/23
 */
@Dao
interface StockDao {
    // insert function
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(stock: Stock)
    // update the amount of stock bought
    @Update
    suspend fun update(stock: Stock)

    // delete stock if you don't want it
    @Delete
    suspend fun delete(stock: Stock)

    // get the specific selected stock
    @Query("SELECT * FROM stock_table WHERE id = :id")
    fun get(id: Int): Flow<Stock>

    // get a list of all of the stocks listed
    @Query("SELECT * FROM stock_table ORDER BY id ASC")
    fun getAll(): Flow<List<Stock>>
}