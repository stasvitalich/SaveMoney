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
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

// The ListFragment class is responsible for displaying a list of grocery items and
// allowing users to add new items.
class ListFragment : Fragment(), GroceryAdapter.ItemClickListener {

    private lateinit var binding: FragmentListBinding
    private lateinit var groceryViewModel: GroceryViewModel
    private lateinit var groceryAdapter: GroceryAdapter

    override fun onItemClicked(updateItem: GroceryEntity) {
        updateItem.isCompleted = !updateItem.isCompleted
        groceryViewModel.updateGrocery(updateItem)
    }

    override fun onDeleteClicked(deleteItem: GroceryEntity){
        lifecycleScope.launch {
            groceryViewModel.deleteGrocery(deleteItem)
        }
    }

    // Inflate the layout using the FragmentListBinding class.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    // Initialize the view elements and set up the ViewModel and RecyclerView.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the grocery adapter for the RecyclerView.
        groceryAdapter = GroceryAdapter()

        groceryAdapter.itemClickListener = this

        // Set up the save button click listener.
        binding.imageSaveButton.setOnClickListener {
            val userInput = binding.inputEditText.text.toString()
            if (userInput.isNotEmpty()) {
                val newGrocery = GroceryEntity(name = userInput)
                groceryViewModel.insertGrocery(newGrocery)
                binding.inputEditText.setText("") // Clear input field after saving item.
            }
        }

        // The Enter button on the keyboard now adds new elements.
        binding.inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                var userInput = binding.inputEditText.text.toString()
                if (userInput.isNotEmpty()){
                    val newGrocery = GroceryEntity(name = userInput)
                    groceryViewModel.insertGrocery(newGrocery)
                    binding.inputEditText.setText("") // Clear input field after saving item.
                }
                true
            }else{
                false
            }
        }

        // Initialize the GroceryDao, repository, and custom ViewModel factory.
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

        // Obtain the GroceryViewModel instance using the custom factory.
        groceryViewModel = ViewModelProvider(this, viewModelFactory).get(GroceryViewModel::class.java)

        // Set up the RecyclerView's layout manager and adapter.
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = groceryAdapter

        // Observe changes to the groceries LiveData and update the adapter accordingly.
        groceryViewModel.groceries.observe(viewLifecycleOwner) { groceries ->
            groceries?.let {
                groceryAdapter.setGroceries(it)
            }
        }
    }

    // Create a new instance of the ListFragment.
    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}