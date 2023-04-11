package com.example.savemoney.screens

import GroceryRepository
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.savemoney.R
import com.example.savemoney.adapters.GroceryAdapter
import com.example.savemoney.data.dao.GroceryDao
import com.example.savemoney.data.database.GroceryDatabase
import com.example.savemoney.data.entities.GroceryEntity
import com.example.savemoney.databinding.FragmentListBinding
import com.example.savemoney.viewmodels.GroceryViewModel

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var groceryViewModel: GroceryViewModel
    private lateinit var groceryAdapter: GroceryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        groceryAdapter = GroceryAdapter()

        binding.imageSaveButton.setOnClickListener {
            val userInput = binding.inputEditText.text.toString()
            if (userInput.isNotEmpty()) {
                val newGrocery = GroceryEntity(name = userInput)
                groceryViewModel.insertGrocery(newGrocery)
                binding.inputEditText.setText("") // Очистить поле ввода после сохранения элемента
            }
        }

        val groceryDao: GroceryDao = GroceryDatabase.getDatabase(requireActivity().applicationContext).groceryDao()
        val repository = GroceryRepository(groceryDao)
        val viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(GroceryViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return GroceryViewModel(repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

        groceryViewModel = ViewModelProvider(this, viewModelFactory).get(GroceryViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = groceryAdapter

        groceryViewModel.groceries.observe(viewLifecycleOwner) { groceries ->
            groceries?.let {
                groceryAdapter.setGroceries(it)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}