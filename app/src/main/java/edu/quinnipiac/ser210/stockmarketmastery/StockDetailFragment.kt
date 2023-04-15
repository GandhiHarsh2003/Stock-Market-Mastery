package edu.quinnipiac.ser210.stockmarketmastery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.quinnipiac.ser210.stockmarketmastery.databinding.FragmentStockDetailBinding
import okhttp3.Response
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback


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
        ApiInterface.create().stockSearchPrice(symbol).enqueue(object : Callback<StockDetailsData> {
            override fun onResponse(
                call: Call<StockDetailsData>, response: retrofit2.Response<StockDetailsData>
            ) {
                if (response.isSuccessful) {
                    val stockDetailsData = response.body()

                    // If the response body is not null, add it to the adapter
                    stockDetailsData?.let { stockAdapter.updateStocks(listOf(it)) }
                }
            }

            override fun onFailure(call: Call<StockDetailsData>, t: Throwable) {
                // Handle failure case
                t.message?.let { Log.d("onFailure", it) }
            }
        })
    }
}

//        // Call the API and update the data in the adapter
//        val apiInterface = ApiInterface.create().stockSearchPrice()
//
//        if (apiInterface != null) {
//            apiInterface.enqueue(object : Callback<StockDetailsData>{
//                // if callback was successful, set the country adapter's list to list of countries response
//                override fun onResponse(call: Call<StockDetailsData>, response: retrofit2.Response<StockDetailsData>) {
//                    if (response.isSuccessful) {
//                        val stockResponse: StockDetailsData? = response.body()
//                        if (stockResponse != null && stockResponse.quoteStock.isNotEmpty()) {
//                            stockAdapter.updateStocks(stockResponse.quoteStock)
//                        }
//                    }
//                }
//
//            override fun onFailure(call: Call<StockDetailsData>, t: Throwable) {
//                    // Handle failure case
//                    t.message?.let { Log.d("onFailure", it) }
//                }
//        })