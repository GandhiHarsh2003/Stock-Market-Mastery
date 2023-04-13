package edu.quinnipiac.ser210.stockmarketmastery

data class StockProfile(
    val CEO: String,
    val address: String,
    val city: String,
    val country: String,
    val description: String,
    val employees: Int,
    val exchange: String,
    val industry: String,
    val mic_code: String,
    val name: String,
    val phone: String,
    val sector: String,
    val state: String,
    val symbol: String,
    val type: String,
    val website: String,
    val zip: String
)