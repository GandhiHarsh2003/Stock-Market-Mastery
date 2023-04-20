package edu.quinnipiac.ser210.stockmarketmastery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * About Fragment Responsible for informing the user the purpose of what the app is about (not much to it.)
 * @author Kevin Rodriguez and Harsh Gandhi
 * Date: 4/20/23
 */
class AboutFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

}