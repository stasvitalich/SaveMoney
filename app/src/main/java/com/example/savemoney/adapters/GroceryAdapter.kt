package com.example.savemoney.adapters

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Update
import com.example.savemoney.data.entities.GroceryEntity
import com.example.savemoney.databinding.GroceryItemBinding
import android.graphics.Paint
import androidx.core.content.ContextCompat
import com.example.savemoney.R

// This adapter is responsible for displaying grocery items in a RecyclerView.
class GroceryAdapter : RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder>() {

    interface ItemClickListener{

        fun onItemClicked(updateItem: GroceryEntity){
        }

        fun onDeleteClicked(deleteItem:GroceryEntity){
        }
    }

    var itemClickListener: ItemClickListener? = null

    // A list to store the grocery entities to be displayed in the RecyclerView.
    var groceries: List<GroceryEntity> = ArrayList()

    // ViewHolder class for each grocery item.
    class GroceryViewHolder(private val binding: GroceryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvGroceryName = binding.tvGroceryName
        val backgroundOfLinearLayout = binding.mainLayout
        val imageDeleteButton = binding.imageDeleteButton
    }

    // This method inflates the layout and creates a ViewHolder instance for each grocery item.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val binding = GroceryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroceryViewHolder(binding)
    }

    // This method binds data to the ViewHolder, setting the text of the TextView.
    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        val grocery = groceries[position]

        holder.tvGroceryName.apply {
            text = grocery.name
            paintFlags = if (grocery.isCompleted) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            alpha = if (grocery.isCompleted) 0.5f else 1.0f
        }

        if (grocery.isCompleted){
            holder.tvGroceryName.paintFlags = holder.tvGroceryName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else{
            holder.tvGroceryName.paintFlags = holder.tvGroceryName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        holder.tvGroceryName.setOnClickListener {
            itemClickListener?.onItemClicked(grocery)
        }

        holder.imageDeleteButton.setOnClickListener {
            itemClickListener?.onDeleteClicked(grocery)
        }

        if (position % 2 == 0){
            holder.backgroundOfLinearLayout.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context,
                    R.color.NoteDark))
        }else{
            holder.backgroundOfLinearLayout.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context,
                    R.color.Note))
        }
    }

    // This method returns the number of items in the RecyclerView.
    override fun getItemCount(): Int {
        return groceries.size
    }

    // This method sets the groceries list to a new list and notifies the adapter about data changes.
    @JvmName("setGroceries1")
    fun setGroceries(groceryList: List<GroceryEntity>) {
        this.groceries = groceryList
        notifyDataSetChanged()
    }
}