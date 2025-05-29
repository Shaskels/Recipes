package com.example.recipes.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.recipes.domain.Recipe

class RecipeListCallback(private val oldItems: List<Recipe>, private val newItems: List<Recipe>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition]::class == newItems[newItemPosition]::class
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}