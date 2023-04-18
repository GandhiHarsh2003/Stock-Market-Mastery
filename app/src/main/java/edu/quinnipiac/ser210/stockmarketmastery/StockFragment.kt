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


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class StockFragment : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    lateinit var spinner: Spinner // declare spinner variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_stock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        view.findViewById<Button>(R.id.SearchButton).setOnClickListener(this)
        spinner = view.findViewById<Spinner>(R.id.chooseOption)
        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.stock_options, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val editText = requireView().findViewById<EditText>(R.id.SymbolInput)
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
        val symbol: EditText = requireView().findViewById<EditText>(R.id.SymbolInput)
        if(!TextUtils.isEmpty(symbol.text.toString())){
            val passArgument = symbol.text.toString()
            val action = StockFragmentDirections.actionSearchStockFragmentToStockDetailFragment(passArgument)
            v?.findNavController()?.navigate(action)
        } else {
            Toast.makeText(context, "Please enter a symbol", Toast.LENGTH_SHORT).show()
        }
    }
}