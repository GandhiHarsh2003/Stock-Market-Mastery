package edu.quinnipiac.ser210.stockmarketmastery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

var data = arrayListOf<StockDetailsData>()
var stockIndex = 0
class StockAdapter(private val context: Context, private val navController: NavController): RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    inner class StockViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        val priceTextView: TextView = view.findViewById(R.id.GetAPICurrentPrice)
        val volumeTextView: TextView = view.findViewById(R.id.priceBoughtFor_textview)
        val logoImage: ImageView = view.findViewById(R.id.GetAPILogo)
        val companyname: TextView = view.findViewById(R.id.GetAPICompanyNameSell)
        val stockHigh: TextView = view.findViewById(R.id.GetAPIstockHigh)
        val stockLow: TextView = view.findViewById(R.id.GetAPIstocklow)
        val stockMedian: TextView = view.findViewById(R.id.GetAPIstockMedian)
        val stockChange: TextView = view.findViewById(R.id.GetAPIstockChange)
        val stockClose: TextView = view.findViewById(R.id.GetAPIstockClose)
        val currency: TextView = view.findViewById(R.id.GetAPIstockCurrency)
        val date: TextView = view.findViewById(R.id.GetAPIstockDateTime)
        val exchange: TextView = view.findViewById(R.id.GetAPIstockExchange)
        //boolean
        val marketOpen: TextView = view.findViewById(R.id.GetAPIstockMarketOpen)
        val micCode: TextView = view.findViewById(R.id.GetAPIstockMicCode)
        val percentChange: TextView = view.findViewById(R.id.GetAPIstockPercentChange)
        val previousClose: TextView = view.findViewById(R.id.GetAPIstockPreviousClose)
        val symbol: TextView = view.findViewById(R.id.GetAPIstockSymbol)
        val timeStamp: TextView = view.findViewById(R.id.GetAPIstockTimestamp)



        fun bindData(stock: StockDetailsData) {
            if (stock.realTimePrice != null) {
                priceTextView.text = stock.realTimePrice.price
            } else {
                priceTextView.text = "N/A"
            }
            if (stock.quoteStock != null) {
                volumeTextView.text = stock.quoteStock.volume
            } else {
                volumeTextView.text = "N/A"
            }

            companyname.text = stock.quoteStock.name

            stockHigh.text = stock.quoteStock.high

            stockLow.text = stock.quoteStock.low

            // unfortunately, there's no median endpoint, so it will have to be calculated..
            val highValue = stock.quoteStock.high.toDouble()

            val lowValue = stock.quoteStock.low.toDouble()

            val median = (highValue + lowValue) / 2

            stockMedian.text = median.toString()

            stockChange.text = stock.quoteStock.change

            stockClose.text = stock.quoteStock.close

            currency.text = stock.quoteStock.currency

            date.text = stock.quoteStock.datetime

            exchange.text = stock.quoteStock.exchange

            if (stock.quoteStock.is_market_open){
                marketOpen.text = "OPEN"
            } else {
                marketOpen.text = "CLOSED"
            }

            micCode.text = stock.quoteStock.mic_code

            percentChange.text = stock.quoteStock.percent_change

            previousClose.text = stock.quoteStock.previous_close

            symbol.text = stock.quoteStock.symbol

            timeStamp.text = stock.quoteStock.timestamp.toString()

            Glide.with(context)
                .load(stock.logoStock.url)
                .apply(RequestOptions().centerCrop())
                .into(logoImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stock_item, parent, false)
        return StockViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bindData(data[position])
        stockIndex = position
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateStocks(newStocks: List<StockDetailsData>) {
        data.clear()
        data.addAll(newStocks)
        notifyDataSetChanged()
    }
}