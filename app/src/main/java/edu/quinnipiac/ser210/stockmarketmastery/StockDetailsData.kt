package edu.quinnipiac.ser210.stockmarketmastery

/**
 * StockDetailData class to get endpoints from api
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/20/23
 */
data class StockDetailsData(
    val quoteStock: QuoteStock,
    val realTimePrice: RealTimePrice,
    val logoStock: LogoStock
)