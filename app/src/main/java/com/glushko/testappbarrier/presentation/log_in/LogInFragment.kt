package com.glushko.testappbarrier.presentation.log_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.glushko.testappbarrier.R
import com.glushko.testappbarrier.presentation.base.BaseFragment
import com.glushko.testappbarrier.databinding.FragmentLogInBinding
import com.glushko.testappbarrier.utils.extensions.setSafeOnClickListener

class LogInFragment : BaseFragment<FragmentLogInBinding>(R.layout.fragment_log_in) {

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLogInBinding = FragmentLogInBinding.inflate(inflater)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {

    }

    private fun setupListeners() = binding.run {
        buttonLogIn.setSafeOnClickListener {

        }
        textSignUp.setSafeOnClickListener {
            navigate(R.id.signUpFragment)
        }

    }
}