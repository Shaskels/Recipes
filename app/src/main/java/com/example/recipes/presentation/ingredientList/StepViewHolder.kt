package com.example.recipes.presentation.ingredientList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.entity.Step

class StepViewHolder(view: View): RecyclerView.ViewHolder(view){
    private val recipeSteps = view.findViewById<TextView>(R.id.steps)

    fun bind(steps: List<Step>){
        var instruction = ""
        for (s in steps){
            instruction += "Step ${s.number}. ${s.description}\n"
        }
        recipeSteps.text = instruction
    }

    companion object {
        fun create(parent: ViewGroup): StepViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.steps_in_list, parent, false)
            return StepViewHolder(view)
        }
    }

}