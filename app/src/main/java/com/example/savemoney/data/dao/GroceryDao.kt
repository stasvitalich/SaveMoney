package com.example.savemoney.data.dao

import androidx.room.*
import com.example.savemoney.data.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GroceryDao {

    @Insert
    suspend fun insert(productEntity: ProductEntity)

    @Update
    suspend fun update(productEntity: ProductEntity)

    @Delete
    suspend fun delete(productEntity: ProductEntity)

    @Query("SELECT * FROM Grocery_table")
    fun fetchAllGroceries():Flow<List<ProductEntity>>

    @Query("SELECT * FROM Grocery_table where id=:id")
    fun fetchGroceriesById(id:Int):Flow<ProductEntity>

}