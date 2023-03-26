package com.example.savemoney.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.savemoney.R
import com.example.savemoney.databinding.FragmentChoose2Binding



class ChooseFragment2 : Fragment() {

    lateinit var binding: FragmentChoose2Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChoose2Binding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.singInButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_chooseFragment2_to_loginFragment)
        }
    }

      companion object {
        @JvmStatic
        fun newInstance() = ChooseFragment2()
    }
}