package com.example.savemoney

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.savemoney.databinding.ActivityChooseBinding



class ChooseActivity : AppCompatActivity() {

    lateinit var binding: ActivityChooseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}