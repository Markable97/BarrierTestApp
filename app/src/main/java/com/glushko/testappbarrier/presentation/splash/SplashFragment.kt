package com.glushko.testappbarrier.presentation.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.glushko.testappbarrier.R
import com.glushko.testappbarrier.repository.user.UserAuthStorage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    @Inject
    lateinit var userAuthStorage: UserAuthStorage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.nav_graph_main, true)
            .build()

        val idNavigate = if (userAuthStorage.isAuth) {
            R.id.profileFragment
        } else {
            R.id.logInFragment
        }

        findNavController().navigate(idNavigate, args = null, navOptions = navOptions)
    }


}