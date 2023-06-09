package edu.quinnipiac.ser210.stockmarketmastery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.quinnipiac.ser210.stockmarketmastery.data.Stock
import edu.quinnipiac.ser210.stockmarketmastery.databinding.SellStockItemBinding

/**
 * SellStockAdpater responsible for displaying the stocks the user might want to sell (NOT FROM SPRINT ONE)
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/20/23
 */
class SellStockAdapter(private val onItemClicked: (Stock) -> Unit) :
    ListAdapter<Stock, SellStockAdapter.ItemViewHolder>(DiffCallback) {


    // initialize view holder for the sell stock adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            SellStockItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }
    // get the items at that current position and then bind the data
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class ItemViewHolder(private var binding: SellStockItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // bind all the data being retrieved to the UI
        fun bind(item: Stock) {
            binding.companyName.text = item.companyName
            binding.stockPrice.text = item.purchased.toString()
            binding.itemQuantity.text = item.stockQuantity.toString()
        }
    }

    // checks if the contents are the same or not
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Stock>() {
            override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean {
                return oldItem.companyName == newItem.companyName
            }
        }
    }
}