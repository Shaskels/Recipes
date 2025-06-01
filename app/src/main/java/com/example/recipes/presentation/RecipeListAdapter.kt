package com.example.recipes.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import com.example.recipes.R
import com.example.recipes.domain.Recipe
import com.squareup.picasso.Picasso

class RecipeListAdapter(private val onItemClick: OnItemClick) :
    PagingDataAdapter<Recipe, RecipeListAdapter.RecipeViewHolder>(RECIPE_DIFF_CALLBACK) {


    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeImage: ImageView
        val recipeName: TextView

        init {
            recipeImage = view.findViewById(R.id.recipeImageView)
            recipeName = view.findViewById(R.id.recipeName)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_in_list, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.recipeName.text = getItem(position)?.name
        Picasso.get().load(getItem(position)?.image).into(holder.recipeImage)
        holder.itemView.setOnClickListener { getItem(position)?.let { item -> onItemClick.onClick(item) } }
    }

    fun interface OnItemClick{
        fun onClick(item: Recipe)
    }
    companion object {
        private val RECIPE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem::class == newItem::class
            }

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem == newItem
            }
        }
    }
}