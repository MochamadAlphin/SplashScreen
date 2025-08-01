package com.example.leaku

import com.example.leaku.R
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val imageBiru = findViewById<ImageView>(R.id.image_biru)

        imageBiru.animate()
            .alpha(1f)
            .setDuration(3000)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                // Setelah animasi selesai, bisa lanjut ke MainActivity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .start()
    }
}

