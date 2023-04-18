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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.max
import kotlin.math.min
import android.util.Log

// The ListFragment class is responsible for displaying a list of grocery items and
// allowing users to add new items.
class ListFragment : Fragment(), GroceryAdapter.ItemClickListener {

    private lateinit var binding: FragmentListBinding
    private lateinit var groceryViewModel: GroceryViewModel
    private lateinit var groceryAdapter: GroceryAdapter
    private var fromPosition: Int? = null

    override fun onItemClicked(updateItem: GroceryEntity) {
        updateItem.isCompleted = !updateItem.isCompleted
        groceryViewModel.updateGrocery(updateItem)
    }

    override fun onDeleteClicked(deleteItem: GroceryEntity) {
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
                lifecycleScope.launch {
                    val newOrder = groceryViewModel.getMaxOrder() + 1
                    val newGrocery = GroceryEntity(name = userInput, order = newOrder)
                    groceryViewModel.insertGrocery(newGrocery)
                }
                binding.inputEditText.setText("") // Clear input field after saving item.
            }
        }

        binding.inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val userInput = binding.inputEditText.text.toString()
                if (userInput.isNotEmpty()) {
                    lifecycleScope.launch {
                        val newOrder = groceryViewModel.getMaxOrder() + 1
                        val newGrocery = GroceryEntity(name = userInput, order = newOrder)
                        groceryViewModel.insertGrocery(newGrocery)
                    }
                    binding.inputEditText.setText("") // Clear input field after saving item.
                }
                true
            } else {
                false
            }
        }


        // Initialize the GroceryDao, repository, and custom ViewModel factory.
        val groceryDao: GroceryDao =
            GroceryDatabase.getDatabase(requireActivity().applicationContext).groceryDao()
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
        groceryViewModel =
            ViewModelProvider(this, viewModelFactory).get(GroceryViewModel::class.java)



        // Set up the RecyclerView's layout manager and adapter.
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = groceryAdapter

        // Observe changes to the groceries LiveData and update the adapter accordingly.
        groceryViewModel.groceries.observe(viewLifecycleOwner) { groceries ->
            groceries?.let {
                groceryAdapter.setGroceries(it.sortedBy { grocery -> grocery.order })
            }
        }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or
                    ItemTouchHelper.DOWN, 0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                source: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val sourcePosition = source.adapterPosition
                val targetPosition = target.adapterPosition

                Collections.swap(groceryAdapter.groceries, sourcePosition, targetPosition)
                groceryAdapter.notifyItemMoved(sourcePosition, targetPosition)

                if (fromPosition == null) {
                    fromPosition = sourcePosition
                }

                return true
            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)

                val toPosition = viewHolder.adapterPosition
                if (fromPosition != null) {
                    lifecycleScope.launch {
                        val updatedItems = groceryViewModel.updateOrdersInDatabase(groceryAdapter.groceries, fromPosition!!, toPosition)
                        groceryAdapter.setGroceries(updatedItems)
                    }
                }
                fromPosition = null
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                TODO("Not yet implemented")
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    // Create a new instance of the ListFragment.
    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}