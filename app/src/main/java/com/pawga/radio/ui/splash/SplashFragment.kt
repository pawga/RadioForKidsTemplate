package com.pawga.radio.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.pawga.radio.R
import com.pawga.radio.ui.RadioforChildrenActivity
import kotlinx.android.synthetic.main.splash_fragment.*


class SplashFragment : Fragment() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    private lateinit var viewModel: SplashViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.splash_fragment, container, false)

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)

        viewModel.needClose.observe(viewLifecycleOwner, Observer {
            if(it) {
                val activityTemp = activity as? RadioforChildrenActivity
                activityTemp?.apply {
                    supportActionBar?.show()
                }
                findNavController().navigate(R.id.nav_home)
            } else {
                val activityTemp = activity as? RadioforChildrenActivity
                activityTemp?.apply {
                    supportActionBar?.hide()
                }
            }
        })

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageViewSplash.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LOW_PROFILE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
}
