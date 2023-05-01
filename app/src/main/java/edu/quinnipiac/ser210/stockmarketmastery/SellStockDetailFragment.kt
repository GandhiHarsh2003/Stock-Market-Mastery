package edu.quinnipiac.ser210.stockmarketmastery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import edu.quinnipiac.ser210.stockmarketmastery.data.Stock
import edu.quinnipiac.ser210.stockmarketmastery.databinding.FragmentSellStockDetailBinding



/**
 * SellStockDetail Fragment responsible for displaying stock and allowing the user to sell their stock.
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/27/2023
 */
class SellStockDetailFragment : Fragment() {

   //
    private val navigationArgs: SellStockDetailFragmentArgs by navArgs()
    lateinit var stock: Stock

    // create instance of the view model
    private val viewModel: StockDetailsViewModel by activityViewModels {
        StockDetailsViewModelFactory(
            (activity?.application as StockApplication).database.stockDao()
        )
    }

    private var _binding: FragmentSellStockDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    // inflates layout used for this fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellStockDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    // bind all the data to the UI
    private fun bind(stock: Stock) {
        binding.apply {
            companyName.text = stock.companyName
            stockPrice.text = stock.purchased.toString()
            stockQuantity.text = stock.stockQuantity.toString()
            sellItem.isEnabled = viewModel.isStockAvailable(stock)
            sellItem.setOnClickListener {
                val quant: String = binding.quantityStock.text.toString()
                val sellPrice: String = binding.sellingPrice.text.toString()
                if(sellPrice != "" && quant != "") {
                    var profitMade: Float = viewModel.sellItem(requireContext(), stock, sellPrice, stock.purchased.toString(), quant)
                    var add = binding.profit.text.toString().toFloat()
                    var calc = profitMade + add
                    binding.profit.text = calc.toString()
                }
            }
            deleteItem.setOnClickListener { showConfirmationDialog() }
        }
    }

    // message that will allow users to ensure if there want to get rid of that stock
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage("Are you sure you want to delete this entry?")
            .setCancelable(false)
            .setNegativeButton("No") { _, _ -> }
            .setPositiveButton("yes") { _, _ ->
                deleteItem()
            }
            .show()
    }

    // deletes the stock from the database
    private fun deleteItem() {
        viewModel.deleteStock(stock)
        findNavController().navigateUp()
    }

    // gets specific stock being retrieved from the database
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
            stock = selectedItem
            bind(stock)
        }
    }

    // destroys the view as soon as soon as the app shuts down
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}