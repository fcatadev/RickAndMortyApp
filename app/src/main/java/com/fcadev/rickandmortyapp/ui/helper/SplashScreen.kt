package com.fcadev.rickandmortyapp.ui.helper

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fcadev.rickandmortyapp.MainActivity
import com.fcadev.rickandmortyapp.R

@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val landingImg: LinearLayout = findViewById(R.id.llSplashScreen)
        val sideAnimation = AnimationUtils.loadAnimation(this, R.anim.slide)
        landingImg.startAnimation(sideAnimation)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true)

        if (isFirstRun) {
            sharedPreferences.edit().putBoolean("isFirstRun", false).apply()
            findViewById<TextView>(R.id.tvSplashScreen).text = "Welcome"
        } else {
            findViewById<TextView>(R.id.tvSplashScreen).text = "Hello"
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
