package edu.quinnipiac.ser210.stockmarketmastery.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * data class stock responsible for being a row within the database (NOT FROM SPRINT ONE)
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/20/23
 */
@Entity(tableName = "stock_table")
data class Stock(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "company_name")
    var companyName: String,
    @ColumnInfo(name = "purchased")
    var purchased: Double,
    @ColumnInfo(name = "stockQuantity")
    var stockQuantity: Int,
)