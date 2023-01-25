package com.glushko.testappbarrier.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.glushko.testappbarrier.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.nav_graph_main, true)
            .build()
        findNavController().navigate(R.id.logInFragment, args = null, navOptions = navOptions)
    }


}