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


class StockAdapter(private val context: Context, private val navController: NavController): RecyclerView.Adapter<StockAdapter.StockViewHolder>() {
    private var data = mutableListOf<StockDetailsData>()

    inner class StockViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        val priceTextView: TextView = view.findViewById(R.id.GetAPICurrentPrice)
        val volumeTextView: TextView = view.findViewById(R.id.GetAPIAverageVolume)
        val logoImage: ImageView = view.findViewById(R.id.GetAPILogo)

        fun bindData(stock: StockDetailsData) {
            if (stock.realTimePrice != null) {
                priceTextView.text = stock.realTimePrice.price.toString()
            } else {
                priceTextView.text = "N/A"
            }
            volumeTextView.text = stock.quoteStock.volume.toString()

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