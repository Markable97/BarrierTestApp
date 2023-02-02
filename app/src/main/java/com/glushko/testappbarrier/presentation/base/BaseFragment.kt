package com.glushko.testappbarrier.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.glushko.testappbarrier.R
import com.glushko.testappbarrier.data.datasource.network.model.UnsuccessfulResponseException
import com.glushko.testappbarrier.utils.extensions.toast
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException

abstract class BaseFragment<B : ViewBinding>(@LayoutRes layout: Int) : Fragment(layout) {

    private var _viewBinding: B? = null
    protected val binding get() = checkNotNull(_viewBinding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = initBinding(inflater, container)
        return binding.root
    }

    abstract fun initBinding(inflater: LayoutInflater, container: ViewGroup?): B

    fun navigate(@IdRes resId: Int, args: Bundle? = null, navOption: NavOptions? = null) {
        findNavController().navigate(resId = resId, args = args, navOptions = navOption)
    }

    fun showErrors(ex: Exception) {
        when (ex) {
            is UnknownHostException -> {
                toast(requireContext(), R.string.network_error)
            }
            is UnsuccessfulResponseException -> {
                toast(requireContext(), ex.errorMessage)
            }
            is CancellationException -> {
                Timber.e("Все норм")
                Timber.e("${ex.message}")
            }
            is SocketTimeoutException -> {
                toast(requireContext(), "Запрос обрабатывается слишком долго")
            }
            else -> {
                toast(requireContext(), ex.message ?: getString(R.string.network_error_default))
                Timber.e("${ex.printStackTrace()}")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }
}
