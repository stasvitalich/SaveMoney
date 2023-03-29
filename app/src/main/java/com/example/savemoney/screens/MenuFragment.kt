package com.example.savemoney.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.savemoney.R
import com.example.savemoney.databinding.FragmentMenuBinding
import androidx.navigation.Navigation

class MenuFragment : Fragment() {

    lateinit var binding: FragmentMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.compare -> {
                    Navigation.findNavController(requireView()).navigate(R.id.menuFragment)
                    true
                }

                R.id.list -> {
                    Navigation.findNavController(requireView()).navigate(R.id.action_menuFragment_to_listFragment)
                    true
                }

                R.id.expenditure -> {
                    Navigation.findNavController(requireView()).navigate(R.id.action_menuFragment_to_expenditureFragment)
                    true
                }

                R.id.settings -> {
                    Navigation.findNavController(requireView()).navigate(R.id.action_menuFragment_to_settingsFragment)
                    true
                }

                else -> false
            }
        }*/
    }

    companion object {
        @JvmStatic
        fun newInstance() = MenuFragment()
    }
}