package com.example.savemoney

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.savemoney.databinding.ActivityLauncherBinding
import render.animations.*

class LauncherActivity : AppCompatActivity() {

    lateinit var binding: ActivityLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            imageLogo.alpha = 0f
        }

        binding.imageLogo.postDelayed({
            binding.imageLogo.animate()
                .alpha(1f)
                .setDuration(200)
                .start()
            val render = Render(this)
            render.setAnimation(Zoom().In(binding.imageLogo))
            render.start()
        }, 500)

    }
}