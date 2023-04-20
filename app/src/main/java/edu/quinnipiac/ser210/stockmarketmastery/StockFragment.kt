package edu.quinnipiac.ser210.stockmarketmastery

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import edu.quinnipiac.ser210.stockmarketmastery.databinding.FragmentSearchStockBinding
import edu.quinnipiac.ser210.stockmarketmastery.databinding.FragmentStockDetailBinding


/**
 * StockFragment responsible for displaying having user input a stock companies name
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/20/23
 */
class StockFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    lateinit var spinner: Spinner // declare spinner variable
    lateinit var binding: FragmentSearchStockBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // initialize binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchStockBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // initialize nav controller
        navController = Navigation.findNavController(view)
        // set an onclick listener for search buttons
        binding.SearchButton.setOnClickListener(this)
        // initialize spinner
        spinner =   binding.chooseOption
        // initialize array adapter and have it list out options from the spinner item
        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.stock_options, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.chooseOption.adapter = adapter
        // listener based on the option selected from the user
        binding.chooseOption.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val editText = binding.SymbolInput
                if(parent?.getItemAtPosition(position).toString().equals("Optional")){
                    editText.setText("")
                } else {
                    editText.setText(parent?.getItemAtPosition(position).toString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    override fun onClick(v: View?) {
        val symbol = binding.SymbolInput
        // checks to see if a company's symbol was entered
        if(!TextUtils.isEmpty(symbol.text.toString())){
            val passArgument = symbol.text.toString()
            // if so, then pass the companies symbol to the stock details screen
            val action = StockFragmentDirections.actionSearchStockFragmentToStockDetailFragment(passArgument)
            v?.findNavController()?.navigate(action)
        } else {
            // if not, kindly ask for user input or have the user pick an option from the spinner
            Toast.makeText(context, "Please enter a symbol", Toast.LENGTH_SHORT).show()
        }
    }
}