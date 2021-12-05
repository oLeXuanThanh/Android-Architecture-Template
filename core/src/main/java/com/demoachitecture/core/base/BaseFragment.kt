package com.demoachitecture.core.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseFragment: Fragment() {

    abstract fun getViewModel(): BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeTokenExpired(getViewModel())
    }

    private fun observeTokenExpired(viewModel: BaseViewModel) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.tokenExpired.collect {
                    if (it.getContentIfNotHandled() == true) {
                        //Goto AuthActivity
                    }
                }
            }
        }
    }
}