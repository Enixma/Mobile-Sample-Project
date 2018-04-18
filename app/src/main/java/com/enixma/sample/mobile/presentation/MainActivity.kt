package com.enixma.sample.mobile.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.enixma.sample.mobile.presentation.pager.MobilePagerActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(MobilePagerActivity.getIntent(this))
        finish()
    }
}
