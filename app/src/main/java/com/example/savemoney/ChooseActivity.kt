package com.example.savemoney

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.savemoney.databinding.ActivityChooseBinding
import com.example.savemoney.screens.ChooseFragment2


class ChooseActivity : AppCompatActivity() {

    lateinit var binding: ActivityChooseBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}