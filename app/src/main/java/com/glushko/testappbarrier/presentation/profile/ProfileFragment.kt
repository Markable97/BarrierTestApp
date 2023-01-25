package com.glushko.testappbarrier.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import com.glushko.testappbarrier.R
import com.glushko.testappbarrier.data.network.UnsuccessfulResponseException
import com.glushko.testappbarrier.databinding.FragmentProfileBinding
import com.glushko.testappbarrier.presentation.base.BaseFragment
import com.glushko.testappbarrier.utils.extensions.setSafeOnClickListener
import com.glushko.testappbarrier.utils.Result
import com.glushko.testappbarrier.utils.extensions.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val viewModel: ProfileViewModel by viewModels()


    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSignOut.setSafeOnClickListener {
            viewModel.signOut()
            navigateToLogInFragment()
        }
        viewModel.liveDataUserInfo.observe(viewLifecycleOwner){
            binding.buttonSignOut.showProgress(it is Result.Loading)
            when(it){
                is Result.Error -> {
                    if (it.exception is UnsuccessfulResponseException.UnauthorizedException) {
                        toast(requireContext(), R.string.network_error_unauthorized)
                        navigateToLogInFragment()
                    } else {
                        showErrors(it.exception)
                    }
                }
                Result.Loading -> {}
                is Result.Success -> {
                    binding.textView.text = it.data.toString()
                }
            }
        }
    }

    private fun navigateToLogInFragment(){
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.nav_graph_main, true)
            .build()
        navigate(R.id.logInFragment, navOption = navOptions)
    }

}