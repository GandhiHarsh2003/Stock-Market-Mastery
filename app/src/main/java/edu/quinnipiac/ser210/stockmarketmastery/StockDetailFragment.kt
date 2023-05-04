package edu.quinnipiac.ser210.stockmarketmastery

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.quinnipiac.ser210.stockmarketmastery.data.Stock
import edu.quinnipiac.ser210.stockmarketmastery.databinding.FragmentStockDetailBinding
import retrofit2.Call
import retrofit2.Callback

/**
 * StockDetailFragment responsible for displaying the data that's being added
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/20/23
 */
var quote: QuoteStock? = null
var sym: LogoStock? = null
var cp: RealTimePrice? = null

class StockDetailFragment : Fragment() {
    // symbol of stock company
    lateinit var symbol: String
    private var binding: FragmentStockDetailBinding? = null
    // adapter to be used within the fragment
    private lateinit var stockAdapter: StockAdapter
    // initialize view model
    private val viewModel: StockDetailsViewModel by activityViewModels {
        StockDetailsViewModelFactory(
            (activity?.application as StockApplication).database.stockDao()
        )
    }
    lateinit var stock: Stock
     var stockIndex = 0
    //     private val navigationArgs: ItemDetailFragmentArgs by navArgs()


    // get the stock symbol passed in
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            symbol = StockDetailFragmentArgs.fromBundle(it).UserStockSymbol.toString()
        }
    }

    // initialize binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStockDetailBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the stockAdapter with an empty list
        stockAdapter = StockAdapter(requireContext(), findNavController())

        // Set the adapter to the RecyclerView
        binding?.stockRecyclerView?.layoutManager = LinearLayoutManager(context)
        binding?.stockRecyclerView?.adapter = stockAdapter

        // Call the API to get the stock details for the entered symbol
        ApiInterface.create().stockRealTimePrice(symbol).enqueue(object : Callback<RealTimePrice> {
            override fun onResponse(
                call: Call<RealTimePrice>, response: retrofit2.Response<RealTimePrice>
            ) {
                // get real time price
                if (response.isSuccessful) {
                    val realTimePrice = response.body()
                    println("THis is is real time" + realTimePrice)
                    cp = realTimePrice
                    pleaseUpdate()
                }
            }

            override fun onFailure(call: Call<RealTimePrice>, t: Throwable) {
                // Handle failure case
                t.message?.let { Log.d("onFailure", it) }
            }
        })

        ApiInterface.create().stockLogos(symbol).enqueue(object : Callback<LogoStock> {
            override fun onResponse(
                call: Call<LogoStock>, response: retrofit2.Response<LogoStock>
            ) {
                // get the logo url (used for loading the companies logo image)
                if (response.isSuccessful) {
                    val logoUrl = response.body()
                    println("THis is is url" + logoUrl)
                    sym = logoUrl
                    pleaseUpdate()
                }
            }

            override fun onFailure(call: Call<LogoStock>, t: Throwable) {
                // Handle failure case
                t.message?.let { Log.d("onFailure", it) }
            }
        })

        ApiInterface.create().stockSearchPrice(symbol).enqueue(object : Callback<QuoteStock> {
            override fun onResponse(
                call: Call<QuoteStock>, response: retrofit2.Response<QuoteStock>
            ) {
                // get the quote stock
                if (response.isSuccessful) {
                    val quoteStock = response.body()
                    println("THis is is qote stock" + quoteStock)
                    quote = quoteStock
                    pleaseUpdate()
                }
            }

            override fun onFailure(call: Call<QuoteStock>, t: Throwable) {
                // Handle failure case
                println("THis is is qote stock")

                t.message?.let { Log.d("onFailure", it) }
            }
        })
        // used to alert the user if stock was bought or not
        binding?.BuyButton?.setOnClickListener{
            if(binding?.QuantityText?.text.toString() != "") {
                addNewItem()
                Toast.makeText(requireContext(), "Bought Stock Successfully", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(requireContext(), "Enter Quantity", Toast.LENGTH_SHORT).show()
            }
        }
    }
    // helper function
    private fun pleaseUpdate(){
        if (quote != null && sym != null && cp != null) {
            updateStockDetails(quote!!, cp!!, sym!!)
        } else {
            println("it is null")
        }
    }

    // update the stocks bought
    private fun updateStockDetails(
        quoteStock: QuoteStock,
        realTimePrice: RealTimePrice,
        logoStock: LogoStock
    ) {
        val stockDetailsData =
            StockDetailsData(
                quoteStock = quoteStock,
                realTimePrice = realTimePrice,
                logoStock = logoStock
            )
        stockAdapter.updateStocks(listOf(stockDetailsData))
    }

    // checks for valid entry input
    private fun isEntryValid(): Boolean{
        return viewModel.isEntryValid(
            binding?.QuantityText.toString()
        )
    }

    // adds stock to db
    private fun addNewItem(){
        if(isEntryValid()){
            viewModel.addNewItem(
                quote?.name.toString(),
                cp?.price.toString(),
                binding?.QuantityText?.text.toString()
            )
        }
    }
    // if it crashes...
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        binding = null
    }
}