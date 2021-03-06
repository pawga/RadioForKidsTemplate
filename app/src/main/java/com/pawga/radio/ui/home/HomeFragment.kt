package com.pawga.radio.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pawga.radio.R
import com.pawga.radio.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_home,
                container,
                false
        )

        binding.lifecycleOwner = this
        binding.viewModel = homeViewModel

        val adapter = RadioStationsAdapter()
        binding.stationsList.adapter = adapter
        homeViewModel.allStations.observe(viewLifecycleOwner, Observer(adapter::submitList))

        return  binding.root
    }
}