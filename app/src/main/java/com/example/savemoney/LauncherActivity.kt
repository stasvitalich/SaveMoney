package com.example.savemoney

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import com.example.savemoney.databinding.ActivityLauncherBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import render.animations.*

class LauncherActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long=4000
    private lateinit var auth: FirebaseAuth
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

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            if (currentUser != null){
                startActivity(Intent(this, AppActivity::class.java))
            } else{
                startActivity(Intent(this, ChooseActivity::class.java))
            }
            finish()

        }, SPLASH_TIME_OUT)

    }
}