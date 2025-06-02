package com.example.recipes.presentation.ingredientList

import androidx.recyclerview.widget.DiffUtil
import com.example.recipes.ui.RecipeFragment

class IngredientListDiffCallback(private val oldItems: List<RecipeFragment.UiModel>,
                                 private val newItems: List<RecipeFragment.UiModel>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return (oldItem is RecipeFragment.UiModel.IngredientItem && newItem is RecipeFragment.UiModel.IngredientItem) ||
                (oldItem is RecipeFragment.UiModel.StepsItem && newItem is RecipeFragment.UiModel.StepsItem)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return oldItem == newItem
    }
}