package com.demoachitecture.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.demoachitecture.core.base.BaseFragment
import com.demoachitecture.core.base.BaseViewModel

class HomeFragment: BaseFragment() {
    override fun getViewModel(): BaseViewModel {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}