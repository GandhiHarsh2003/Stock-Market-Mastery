package edu.quinnipiac.ser210.stockmarketmastery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



/**
 * SellFragment displays the various fragments the user may want to sell (NOT FROM SPRINT ONE)
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/20/23
 */
class SellFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sell, container, false)
    }

}