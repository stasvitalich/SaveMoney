package com.example.savemoney.viewmodels

import GroceryRepository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.savemoney.data.entities.GroceryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.math.min
import kotlin.math.max

class GroceryViewModel(private val repository: GroceryRepository) : ViewModel() {
    val groceries: LiveData<List<GroceryEntity>> = repository.fetchAllGroceries().asLiveData()

    fun insertGrocery(grocery: GroceryEntity) {
        viewModelScope.launch {
            repository.insert(grocery)
        }
    }

    fun updateGrocery(grocery: GroceryEntity) {
        viewModelScope.launch {
            repository.update(grocery)
        }
    }

    fun deleteGrocery(grocery: GroceryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGrocery(grocery)
        }
    }

    suspend fun updateOrdersInDatabase(items: List<GroceryEntity>, fromPosition: Int, toPosition: Int): List<GroceryEntity> {
        val minPosition = min(fromPosition, toPosition)
        val maxPosition = max(fromPosition, toPosition)
        val updatedItems = mutableListOf<GroceryEntity>()
        for (i in minPosition..maxPosition) {
            val item = items[i]
            val newPosition = i
            if (item.order != newPosition) {
                item.order = newPosition
                repository.update(item)
                updatedItems.add(item)
                Log.d("updateOrdersInDatabase", "Updated item: $item")
            }
        }
        return items
    }

    suspend fun getMaxOrder(): Int {
        return repository.getMaxOrder() ?: 0
    }
}