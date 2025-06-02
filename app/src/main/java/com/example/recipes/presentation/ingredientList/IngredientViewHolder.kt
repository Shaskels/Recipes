package com.example.recipes.presentation.ingredientList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.Ingredient

class IngredientViewHolder(view: View): RecyclerView.ViewHolder(view){
    private val name = view.findViewById<TextView>(R.id.ingredientName)
    private val amount = view.findViewById<TextView>(R.id.ingredientAmount)
    private val unit = view.findViewById<TextView>(R.id.ingredientUnit)

    fun bind(ingredient: Ingredient){
        name.text = ingredient.name
        amount.text = ingredient.amount.toString()
        unit.text = ingredient.unit
    }

    companion object {
        fun create(parent: ViewGroup): IngredientViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.ingredient_in_list, parent, false)
            return IngredientViewHolder(view)
        }
    }
}