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
import edu.quinnipiac.ser210.stockmarketmastery.databinding.StockItemBinding

/**
 * StockAdapter responsible for being able to bind data retrieved from API
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/20/23
 */
var data = arrayListOf<StockDetailsData>()
var stockIndex = 0
class StockAdapter(private val context: Context, private val navController: NavController): RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    inner class StockViewHolder(private var binding: StockItemBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
        // bind the data retrieved from api and them to the recycle view components
        fun bindData(stock: StockDetailsData) {
            if (stock.realTimePrice != null) {
                binding.GetAPICurrentPrice.text = stock.realTimePrice.price
            } else {
                binding.GetAPICurrentPrice.text = "N/A"
            }

            if (stock.quoteStock != null) {
                binding.GetAPIAverageVolume.text = stock.quoteStock.volume
            } else {
                binding.GetAPIAverageVolume.text = "N/A"
            }

            binding.GetAPICompanyNameSell.text = stock.quoteStock.name

            binding.GetAPIstockHigh.text = stock.quoteStock.high

            binding.GetAPIstocklow.text = stock.quoteStock.low

            // unfortunately, there's no median endpoint, so it will have to be calculated..
            val highValue = stock.quoteStock.high.toDouble()

            val lowValue = stock.quoteStock.low.toDouble()

            val median = (highValue + lowValue) / 2

            binding.GetAPIstockMedian.text = median.toString()

            binding.GetAPIstockChange.text = stock.quoteStock.change

            binding.GetAPIstockClose.text = stock.quoteStock.close

            binding.GetAPIstockCurrency.text = stock.quoteStock.currency

            binding.GetAPIstockDateTime.text = stock.quoteStock.datetime

            binding.GetAPIstockExchange.text = stock.quoteStock.exchange

            if (stock.quoteStock.is_market_open){
                binding.GetAPIstockMarketOpen.text = "OPEN"
            } else {
                binding.GetAPIstockMarketOpen.text = "CLOSED"
            }

            binding.GetAPIstockMicCode.text = stock.quoteStock.mic_code

            binding.GetAPIstockPercentChange.text = stock.quoteStock.percent_change

            binding.GetAPIstockPreviousClose.text = stock.quoteStock.previous_close

            binding.GetAPIstockSymbol.text = stock.quoteStock.symbol

            binding.GetAPIstockTimestamp.text = stock.quoteStock.timestamp.toString()

            Glide.with(context)
                .load(stock.logoStock.url)
                .apply(RequestOptions().centerCrop())
                .into(binding.GetAPILogo)
        }
    }
    // inflates the stock item layout and instantiates stock view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val action = StockItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StockViewHolder(action , context)
    }

    // will simply bind data at a certain index by calling helper function
    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bindData(data[position])
        stockIndex = position
    }

    // get the size of array storing the data 
    override fun getItemCount(): Int {
        return data.size
    }

    // helper function for the onBindViewHolder
    fun updateStocks(newStocks: List<StockDetailsData>) {
        data.clear()
        data.addAll(newStocks)
        notifyDataSetChanged()
    }
}