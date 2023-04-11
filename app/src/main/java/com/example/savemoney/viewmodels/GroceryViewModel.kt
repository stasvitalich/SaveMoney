package com.example.savemoney.viewmodels

import GroceryRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.savemoney.data.entities.GroceryEntity
import kotlinx.coroutines.launch

class GroceryViewModel(private val repository: GroceryRepository) : ViewModel() {
    private val _groceries = MutableLiveData<List<GroceryEntity>>()
    val groceries: LiveData<List<GroceryEntity>> get() = _groceries

    init {
        fetchAllGroceries()
    }

    private fun fetchAllGroceries() {
        viewModelScope.launch {
            repository.fetchAllGroceries().collect{ groceries ->
                _groceries.value = groceries
            }
        }
    }

    fun insertGrocery(grocery: GroceryEntity) {
        viewModelScope.launch {
            repository.insert(grocery)
            fetchAllGroceries()
        }
    }

    fun updateGrocery(grocery: GroceryEntity) {
        viewModelScope.launch {
            repository.update(grocery)
            fetchAllGroceries()
        }
    }

    fun deleteGrocery(grocery: GroceryEntity) {
        viewModelScope.launch {
            repository.delete(grocery)
            fetchAllGroceries()
        }
    }
}