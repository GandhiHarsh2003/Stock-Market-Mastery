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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(stock: Stock)

    @Update
    suspend fun update(stock: Stock)

    @Delete
    suspend fun delete(stock: Stock)

    @Query("SELECT * FROM stock_table WHERE id = :id")
    fun get(id: Int): Flow<Stock>

    @Query("SELECT * FROM stock_table ORDER BY id ASC")
    fun getAll(): Flow<List<Stock>>
}