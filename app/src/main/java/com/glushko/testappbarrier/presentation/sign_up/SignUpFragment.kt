package com.glushko.testappbarrier.presentation.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.glushko.testappbarrier.R
import com.glushko.testappbarrier.databinding.FragmentSignUpBinding
import com.glushko.testappbarrier.presentation.base.BaseFragment
import com.glushko.testappbarrier.utils.extensions.setSafeOnClickListener
import com.glushko.testappbarrier.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint
import com.glushko.testappbarrier.utils.Result

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>(R.layout.fragment_sign_up) {

    private val viewModel: SignUpViewModel by viewModels()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignUpBinding = FragmentSignUpBinding.inflate(inflater)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() = binding.run {
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        buttonSignUp.setSafeOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val firstName = editTextName.text.toString()
            if (listOf(email, password, firstName).all(String::isNotBlank)) {
                viewModel.singUp(
                    email = email,
                    password = password,
                    name = firstName
                )
            } else {
                toast(requireContext(), R.string.error_empty_fields)
            }
        }
    }

    private fun setupObservers() = viewModel.run{
        eventSignUp.observe(viewLifecycleOwner) {
            binding.buttonSignUp.showProgress(it is Result.Loading)
            when(it){
                is Result.Error -> showErrors(it.exception)
                Result.Loading -> {}
                is Result.Success -> {
                    toast(requireContext(), R.string.sign_up_success)
                    findNavController().popBackStack()
                }
            }
        }

    }

}