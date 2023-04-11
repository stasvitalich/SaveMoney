package com.example.savemoney.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.savemoney.data.entities.GroceryEntity
import com.example.savemoney.databinding.GroceryItemBinding

// This adapter is responsible for displaying grocery items in a RecyclerView.
class GroceryAdapter : RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder>() {

    // A list to store the grocery entities to be displayed in the RecyclerView.
    private var groceries: List<GroceryEntity> = ArrayList()

    // ViewHolder class for each grocery item.
    class GroceryViewHolder(private val binding: GroceryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvGroceryName = binding.tvGroceryName
    }

    // This method inflates the layout and creates a ViewHolder instance for each grocery item.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val binding = GroceryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroceryViewHolder(binding)
    }

    // This method binds data to the ViewHolder, setting the text of the TextView.
    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        val grocery = groceries[position]
        holder.tvGroceryName.text = grocery.name
    }

    // This method returns the number of items in the RecyclerView.
    override fun getItemCount(): Int {
        return groceries.size
    }

    // This method sets the groceries list to a new list and notifies the adapter about data changes.
    fun setGroceries(groceryList: List<GroceryEntity>) {
        this.groceries = groceryList
        notifyDataSetChanged()
    }
}