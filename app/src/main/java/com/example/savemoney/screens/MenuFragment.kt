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
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.compare -> {
                    // Обработка нажатия на item "compare"
                    Navigation.findNavController(view).navigate(R.id.menuFragment)
                    true
                }
                R.id.list -> {
                    // Обработка нажатия на item "list"
                    Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_listFragment)
                    true
                }
                R.id.expenditure -> {
                    // Обработка нажатия на item "expenditure"
                    Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_expenditureFragment)
                    true
                }
                R.id.settings -> {
                    // Обработка нажатия на item "settings"
                    Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_settingsFragment)
                    true
                }
                else -> false
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MenuFragment()
    }
}