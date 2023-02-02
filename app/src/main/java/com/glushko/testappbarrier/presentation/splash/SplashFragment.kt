package com.glushko.testappbarrier.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.glushko.testappbarrier.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.nav_graph_main, true)
            .build()

        viewModel.liveDataIsAuth.observe(viewLifecycleOwner){ isAuth ->
            val idNavigate = if (isAuth) {
                R.id.profileFragment
            } else {
                R.id.logInFragment
            }

            findNavController().navigate(idNavigate, args = null, navOptions = navOptions)
        }

    }


}