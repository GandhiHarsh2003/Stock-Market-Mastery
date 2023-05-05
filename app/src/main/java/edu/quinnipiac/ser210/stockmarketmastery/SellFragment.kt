package edu.quinnipiac.ser210.stockmarketmastery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.quinnipiac.ser210.stockmarketmastery.databinding.FragmentSellBinding

import edu.quinnipiac.ser210.stockmarketmastery.databinding.SellStockItemBinding


/**
 * SellFragment displays the various fragments the user may want to sell (NOT FROM SPRINT ONE)
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/20/23
 */
class SellFragment : Fragment() {

    // initialize view model
    private val viewModel: StockDetailsViewModel by activityViewModels {
        StockDetailsViewModelFactory(
            (activity?.application as StockApplication).database.stockDao()
        )
    }

    private var _binding: FragmentSellBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSellBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // passing the id once you know what stock to click
        val adapter = SellStockAdapter {
            val action = SellFragmentDirections.actionSellFragmentToSellStockDetailFragment(it.id)
            this.findNavController().navigate(action)
        }
        // setting up adapter along with initializing it
        binding.recyclerSellView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerSellView.adapter = adapter
        // Attach an observer on the allItems list to update the UI automatically when the data
        // changes.
        viewModel.allItems.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }
    }

}