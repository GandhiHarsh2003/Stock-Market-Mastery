package edu.quinnipiac.ser210.stockmarketmastery
/**
 * FiftyTwoWeek data class responsible for helping the quote stock data class
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/20/23
 */
data class FiftyTwoWeek(
    val high: String,
    val high_change: String,
    val high_change_percent: String,
    val low: String,
    val low_change: String,
    val low_change_percent: String,
    val range: String
)