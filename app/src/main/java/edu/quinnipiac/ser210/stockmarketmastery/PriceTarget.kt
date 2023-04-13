package edu.quinnipiac.ser210.stockmarketmastery

data class PriceTarget(
    val average: Double,
    val current: Double,
    val high: Int,
    val low: Int,
    val median: Int
)