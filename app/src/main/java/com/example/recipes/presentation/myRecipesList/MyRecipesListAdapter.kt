package com.example.recipes.presentation.myRecipesList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.Recipe
import com.example.recipes.presentation.RecipeListAdapter.OnItemClick
import com.example.recipes.presentation.RecipeListAdapter.OnLikeButtonClick
import com.squareup.picasso.Picasso

class MyRecipesListAdapter(
    private val onItemClick: OnItemClick,
    private val onLikeButtonClick: OnLikeButtonClick
) :
    RecyclerView.Adapter<MyRecipesListAdapter.RecipeViewHolder>() {

    val items = ArrayList<Recipe>()

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

    override fun getItemCount(): Int = items.size

    fun setNewItems(newItems: List<Recipe>){
        val diffUtil = DiffUtilCallback(items, newItems)
        val diff = DiffUtil.calculateDiff(diffUtil)
        items.clear()
        items.addAll(newItems)
        diff.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.recipeName.text = items[position].name
        Picasso.get().load(items[position].image).placeholder(R.drawable.nophoto).error(R.drawable.nophoto).into(holder.recipeImage)
        holder.itemView.setOnClickListener { items[position].let { item -> onItemClick.onClick(item) } }
        if (items[position].isLiked) holder.likeButton.isSelected = true
        holder.likeButton.setOnClickListener {
            holder.likeButton.isSelected = !holder.likeButton.isSelected
            items[position].let { it1 -> onLikeButtonClick.onClick(it1, holder.likeButton.isSelected) }
        }
    }
}