package com.example.savemoney.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
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

    // Close application if user pushes system back button
    // from start destination fragment.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.singInButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_chooseFragment2_to_loginFragment)
        }
        binding.registerButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_chooseFragment2_to_registrationFragment)
        }
    }

      companion object {
        @JvmStatic
        fun newInstance() = ChooseFragment2()
    }
}