package com.example.kmm_demo.android.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ComponentActivity
import com.example.kmm_demo.android.databinding.ActivitySplashBinding
import com.example.kmm_demo.android.ui.weatherlist.MainActivity

class SplashActivity : ComponentActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        moveToNextScreen()

    }

    private fun moveToNextScreen() {
        val handler = Handler()
        handler.postDelayed(Runnable {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 3000)

    }
}