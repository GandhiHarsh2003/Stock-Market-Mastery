package edu.quinnipiac.ser210.stockmarketmastery

/**
 * QuoteStock data class that will use the parameters here for the app
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/20/23
 */
data class QuoteStock(
    val average_volume: String,
    val change: String,
    val close: String,
    val currency: String,
    val datetime: String,
    val exchange: String,
    val fifty_two_week: FiftyTwoWeek,
    val high: String,
    val is_market_open: Boolean,
    val low: String,
    val mic_code: String,
    val name: String,
    val `open`: String,
    val percent_change: String,
    val previous_close: String,
    val symbol: String,
    val timestamp: Int,
    val volume: String
)