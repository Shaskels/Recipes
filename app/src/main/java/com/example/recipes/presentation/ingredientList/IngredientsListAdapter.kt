package com.example.recipes.presentation.ingredientList

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.recipes.R
import com.example.recipes.ui.RecipeFragment

class IngredientsListAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val items: ArrayList<RecipeFragment.UiModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == R.layout.ingredient_in_list) {
            IngredientViewHolder.create(parent)
        } else {
            StepViewHolder.create(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is RecipeFragment.UiModel.IngredientItem -> R.layout.ingredient_in_list
            is RecipeFragment.UiModel.StepsItem -> R.layout.steps_in_list
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setNewItems(newItems: List<RecipeFragment.UiModel>){
        val diffUtil = IngredientListDiffCallback(items, newItems)
        val diff = DiffUtil.calculateDiff(diffUtil)
        items.clear()
        items.addAll(newItems)
        diff.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val uiModel = items[position]){
            is RecipeFragment.UiModel.IngredientItem -> (holder as IngredientViewHolder).bind(uiModel.ingredient)
            is RecipeFragment.UiModel.StepsItem -> (holder as StepViewHolder).bind(uiModel.stepsItem)
        }
    }

}