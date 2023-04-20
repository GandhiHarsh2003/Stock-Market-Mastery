package edu.quinnipiac.ser210.stockmarketmastery.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "stock_table")
data class Stock(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "company_name")
    var companyName: String,
    @ColumnInfo(name = "purchased")
    var purchased: Double,
    @ColumnInfo(name = "stock_bought")
    var stockBought: Boolean = false
)