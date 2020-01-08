package com.pawga.radio.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.pawga.radio.R
import com.pawga.radio.databinding.AboutFragmentBinding


class AboutFragment : Fragment() {

    companion object {
        fun newInstance() = AboutFragment()
    }

    private lateinit var viewModel: AboutViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel = ViewModelProviders.of(this).get(AboutViewModel::class.java)

        val binding: AboutFragmentBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.about_fragment,
                container,
                false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }
}
