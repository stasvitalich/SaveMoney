package com.example.savemoney.data.dao

import androidx.room.*
import com.example.savemoney.data.entities.GroceryEntity
import kotlinx.coroutines.flow.Flow

// DAO interface for working with the Grocery-table.
@Dao
interface GroceryDao {

    // Inserts a new grocery item into the Grocery-table.
    @Insert
    suspend fun insert(grocery: GroceryEntity)

    // Updates an existing grocery item in the Grocery-table.
    @Update
    suspend fun update(grocery: GroceryEntity)

    // Deletes a grocery item from the Grocery-table.
    @Delete
    suspend fun deleteGrocery(grocery: GroceryEntity)

    // Retrieves all grocery items from the Grocery-table.
    // Returns a Flow object containing a list of grocery items.
    @Query("SELECT * FROM Grocery_table")
    fun fetchAllGroceries(): Flow<List<GroceryEntity>>

    // Retrieves a grocery item from the Grocery-table by the given ID.
    // param id The ID of the grocery item to be found.
    // Returns a Flow object containing the found grocery item.
    @Query("SELECT * FROM Grocery_table where id=:id")
    fun fetchGroceriesById(id: Int): Flow<GroceryEntity>
}