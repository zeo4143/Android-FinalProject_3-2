package com.example.food_ordering_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide

class OrderPlacedSplashScreen : AppCompatActivity() {

    lateinit var gifImageView:ImageView
    lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_placed_splash_screen)
        supportActionBar?.hide()

        gifImageView = findViewById(R.id.gifImageView)
        Glide.with(this).load(R.raw.orderplaced).into(gifImageView)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                startActivity(Intent(this, MainActivity::class.java))
            },
            2000
        )
    }
}