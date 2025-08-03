package com.example.recipes.presentation.myRecipesList

import androidx.recyclerview.widget.DiffUtil
import com.example.recipes.domain.entity.Recipe

class DiffUtilCallback(private val oldItems: List<Recipe>,
                       private val newItems: List<Recipe>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return oldItem.idInApi == newItem.idInApi
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return oldItem == newItem
    }
}