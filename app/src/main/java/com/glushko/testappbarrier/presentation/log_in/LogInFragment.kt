package com.glushko.testappbarrier.presentation.log_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.glushko.testappbarrier.R
import com.glushko.testappbarrier.presentation.base.BaseFragment
import com.glushko.testappbarrier.databinding.FragmentLogInBinding
import com.glushko.testappbarrier.utils.extensions.setSafeOnClickListener
import com.glushko.testappbarrier.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint
import com.glushko.testappbarrier.utils.Result
import timber.log.Timber

@AndroidEntryPoint
class LogInFragment : BaseFragment<FragmentLogInBinding>(R.layout.fragment_log_in) {

    private val viewModel: LoginViewModel by viewModels()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLogInBinding = FragmentLogInBinding.inflate(inflater)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() = viewModel.run{
        eventLogIn.observe(viewLifecycleOwner){
            binding.buttonLogIn.showProgress(it is Result.Loading)
            when(it){
                is Result.Error -> {
                    showErrors(it.exception)
                }
                Result.Loading -> {}
                is Result.Success -> {
                    Timber.d("SUCCESS ${it.data}")
                }
            }
        }
    }

    private fun setupListeners() = binding.run {
        buttonLogIn.setSafeOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            if (email.isNotBlank() && password.isNotBlank()) {
                viewModel.logIn(
                    editTextEmail.text.toString(),
                    editTextPassword.resources.toString()
                )
            } else {
                toast(requireContext(), R.string.error_empty_fields)
            }

        }
        textSignUp.setSafeOnClickListener {
            navigate(R.id.signUpFragment)
        }

    }
}