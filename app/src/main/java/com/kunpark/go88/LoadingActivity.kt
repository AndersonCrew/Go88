package com.kunpark.go88

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_loading.*

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        object : CountDownTimer(3600000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvLoading?.text = "Loading ${ millisUntilFinished * 100 / (60 * 60 * 60 * 1000)}%"
            }

            override fun onFinish() {
                tvLoading?.text = "Loading 100%"
            }
        }.start()
    }

    override fun onBackPressed() {

    }
}