package com.example.leaku

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var btnNext: Button
    private lateinit var dotLayout: LinearLayout
    private lateinit var rootLayout: View

    private val images = listOf(R.drawable.satu, R.drawable.dua, R.drawable.tiga)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        btnNext = findViewById(R.id.btnNext)
        dotLayout = findViewById(R.id.dotLayout)
        rootLayout = findViewById(R.id.rootLayout)

        rootLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white))

        viewPager.adapter = ImageSliderAdapter(images)
        viewPager.setPageTransformer(FadePageTransformer())

        // Pastikan dots ditampilkan setelah layout siap
        dotLayout.post {
            addDots(0)
        }

        btnNext.visibility = View.VISIBLE

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                addDots(position)
                btnNext.text = "NEXT"
            }
        })

        btnNext.setOnClickListener {
            val nextItem = viewPager.currentItem + 1
            if (nextItem < images.size) {
                animateToNextPage(nextItem)
            } else {
                Toast.makeText(this, "Selesai!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun animateToNextPage(nextItem: Int) {
        val fadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein)

        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                viewPager.setCurrentItem(nextItem, false)
                viewPager.startAnimation(fadeIn)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        viewPager.startAnimation(fadeOut)
    }

    private fun addDots(position: Int) {
        dotLayout.removeAllViews()
        for (i in images.indices) {
            val dot = ImageView(this)
            val isActive = i == position
            dot.setImageResource(if (isActive) R.drawable.circle_active else R.drawable.circle_inactive)

            val size = if (isActive) 24 else 16
            val params = LinearLayout.LayoutParams(size, size)
            params.setMargins(8, 0, 8, 0)
            dot.layoutParams = params
            dotLayout.addView(dot)

            // Animasi muncul dot
            dot.alpha = 0f
            dot.animate().alpha(1f).setDuration(300).start()
        }
    }

    class FadePageTransformer : ViewPager2.PageTransformer {
        override fun transformPage(page: View, position: Float) {
            page.alpha = 1.0f - abs(position)
            page.translationX = -position * page.width
        }
    }
}
