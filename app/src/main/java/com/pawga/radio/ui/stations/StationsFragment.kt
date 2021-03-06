package com.pawga.radio.ui.stations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pawga.radio.R

class StationsFragment : Fragment() {

    private lateinit var stationsViewModel: StationsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        stationsViewModel =
                ViewModelProviders.of(this).get(StationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        stationsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}