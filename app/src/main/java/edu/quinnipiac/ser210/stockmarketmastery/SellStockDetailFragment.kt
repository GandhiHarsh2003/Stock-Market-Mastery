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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SellStockDetail.newInstance] factory method to
 * create an instance of this fragment.
 */
class SellStockDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val navigationArgs: SellStockDetailFragmentArgs by navArgs()
    lateinit var stock: Stock

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSellStockDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

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
                    viewModel.sellItem(requireContext(), stock, sellPrice, stock.purchased.toString(), quant)
                }
            }
            deleteItem.setOnClickListener { showConfirmationDialog() }
        }
    }

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

    private fun deleteItem() {
        viewModel.deleteStock(stock)
        findNavController().navigateUp()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
            stock = selectedItem
            bind(stock)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}