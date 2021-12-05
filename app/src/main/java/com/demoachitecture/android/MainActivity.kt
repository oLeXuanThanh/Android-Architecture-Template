package com.demoachitecture.android

import android.content.Intent
import android.os.Bundle
import com.demoachitecture.auth.AuthActivity
import com.demoachitecture.core.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}