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

class RecipeListAdapter(private val onItemClick: OnItemClick, private val onLikeButtonClick: OnLikeButtonClick) :
    PagingDataAdapter<Recipe, RecipeListAdapter.RecipeViewHolder>(RECIPE_DIFF_CALLBACK) {


    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeImage: ImageView
        val recipeName: TextView
        val likeButton: ImageView

        init {
            recipeImage = view.findViewById(R.id.recipeImageView)
            recipeName = view.findViewById(R.id.recipeName)
            likeButton = view.findViewById(R.id.imageButton)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_in_list, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.recipeName.text = getItem(position)?.name
        Picasso.get().load(getItem(position)?.image).placeholder(R.drawable.nophoto).error(R.drawable.nophoto).into(holder.recipeImage)
        holder.itemView.setOnClickListener { getItem(position)?.let { item -> onItemClick.onClick(item) } }
        if (getItem(position)?.isLiked == true) holder.likeButton.isSelected = true
        holder.likeButton.setOnClickListener {
            holder.likeButton.isSelected = !holder.likeButton.isSelected
            getItem(position)?.let { it1 -> onLikeButtonClick.onClick(it1, holder.likeButton.isSelected) }
        }
    }

    fun interface OnItemClick{
        fun onClick(item: Recipe)
    }

    fun interface OnLikeButtonClick{
        fun onClick(item: Recipe, isSelected: Boolean)
    }

    companion object {
        private val RECIPE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem.idInApi == newItem.idInApi
            }

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
                return oldItem == newItem
            }
        }
    }
}