package edu.quinnipiac.ser210.stockmarketmastery

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import edu.quinnipiac.ser210.stockmarketmastery.data.Stock
import edu.quinnipiac.ser210.stockmarketmastery.data.StockDao
import edu.quinnipiac.ser210.stockmarketmastery.data.StockDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Stock Database test will test and ensure if the stock data entity is getting added to the database
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/25/23
 * NOTE: Boiler plate provided from Lesson 11 was used
 */
@RunWith(AndroidJUnit4::class)
class StockDatabaseTest {
    // dao, database, stock entities
    private lateinit var dao: StockDao
    private lateinit var stockDataBase: StockDatabase
    private var stockOne: Stock = Stock(
        id = 1,
        companyName = "TSLA",
        purchased = 124.00,
        stockQuantity = 1
    )

    private var stockTwo: Stock = Stock(
        id = 2,
        companyName = "AMZN",
        purchased = 127.00,
        stockQuantity = 1
    )

    // create an instance of database and dao
    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        stockDataBase = Room.inMemoryDatabaseBuilder(
            context, StockDatabase::class.java).allowMainThreadQueries().build()
        dao = stockDataBase.stockDao()
    }

    // will add one item to database
    private suspend fun addOneItemToDb() {
        dao.insert(stockOne)
    }
    // add two items to database
    private suspend fun addTwoItemsToDb() {
        dao.insert(stockOne)
        dao.insert(stockTwo)
    }

    // will ensure if the stock entity's in the database
    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsItemIntoDB() = runBlocking {
        addOneItemToDb()
        val allItems = dao.getAll().first()
        assertEquals(allItems[0], stockOne)
    }

    // will ensure if the stock entity's in the database
    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDB() = runBlocking {
        addTwoItemsToDb()
        val allItems = dao.getAll().first()
        Assert.assertEquals(allItems[0], stockOne)
        Assert.assertEquals(allItems[1], stockTwo)
    }

    // will shut down instance of database
    @After
    @Throws(IOException::class)
    fun closeDb() {
        stockDataBase.close()
    }
}