package edu.quinnipiac.ser210.stockmarketmastery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.quinnipiac.ser210.stockmarketmastery.databinding.FragmentStockDetailBinding
import retrofit2.Call
import retrofit2.Callback


var quote: QuoteStock? = null
var sym: LogoStock? = null
var cp: RealTimePrice? = null

class StockDetailFragment : Fragment() {
    lateinit var symbol: String
    private lateinit var binding: FragmentStockDetailBinding
    private lateinit var stockAdapter: StockAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            symbol = StockDetailFragmentArgs.fromBundle(it).UserStockSymbol.toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStockDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the stockAdapter with an empty list
        stockAdapter = StockAdapter(requireContext(), findNavController())

        // Set the adapter to the RecyclerView
        binding.stockRecyclerView.adapter = stockAdapter
        binding.stockRecyclerView.layoutManager = LinearLayoutManager(context)

        // Call the API to get the stock details for the entered symbol
        ApiInterface.create().stockRealTimePrice(symbol).enqueue(object : Callback<RealTimePrice> {
            override fun onResponse(
                call: Call<RealTimePrice>, response: retrofit2.Response<RealTimePrice>
            ) {
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
    }

    private fun pleaseUpdate(){
        if (quote != null && sym != null && cp != null) {
            updateStockDetails(quote!!, cp!!, sym!!)
        } else {
            println("it is null")
        }
    }
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
}