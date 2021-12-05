package com.demoachitecture.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.demoachitecture.core.base.BaseFragment
import com.demoachitecture.core.base.BaseViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun getViewModel(): BaseViewModel {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bt_goto_home.setOnClickListener {

        }
    }
}