package edu.quinnipiac.ser210.stockmarketmastery

data class StockDetailsData(
    val quoteStock: QuoteStock,
    val realTimePrice: RealTimePrice,
    val logoStock: LogoStock
)