package com.example.savemoney.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.savemoney.data.entities.GroceryEntity

class GroceryViewModel: ViewModel() {

    // MutableLiveData to hold the list of groceries.
    private val _groceries = MutableLiveData<List<GroceryEntity>>()
    // Public LiveData to expose the list of groceries, but not allow modification from outside.
    val groceries: LiveData<List<GroceryEntity>> get() = _groceries

    // Initialize the list of groceries with an empty list.
    init {
        _groceries.value = listOf()
    }

    // Update the list of groceries with a new list
    fun updateGroceries(groceryList: List<GroceryEntity>){
        _groceries.value = groceryList
    }
}