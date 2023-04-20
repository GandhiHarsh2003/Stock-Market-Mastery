package edu.quinnipiac.ser210.stockmarketmastery

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import edu.quinnipiac.ser210.stockmarketmastery.databinding.FragmentSearchStockBinding
import edu.quinnipiac.ser210.stockmarketmastery.databinding.FragmentSplashScreenBinding

/**
 * Splash screen responsible for providing intro to what app is about, nothing fancy.
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/20/23
 */
class SplashScreen : Fragment(), View.OnClickListener {

    lateinit var navController: NavController
    lateinit var binding: FragmentSplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // initialize binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    // find nav controller and initialize on click listener
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.start.setOnClickListener(this)
    }

    // function that sends user from splash screen to search stock fragment
    override fun onClick(v: View?) {
        val action = SplashScreenDirections.actionSplashScreenToSearchStockFragment()
        v?.findNavController()?.navigate(action)
    }

}