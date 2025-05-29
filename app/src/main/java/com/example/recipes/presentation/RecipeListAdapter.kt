package com.example.recipes.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import com.example.recipes.R
import com.example.recipes.domain.Recipe
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import java.util.Locale

class RecipeListAdapter(private var recipes: ArrayList<Recipe>) :
    RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>() {

    val initialRecipes = ArrayList<Recipe>()

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recipeImage: ImageView
        val recipeName: TextView
        val recipeLikes: Chip
        val recipeScore: Chip

        init {
            recipeImage = view.findViewById(R.id.recipeImageView)
            recipeName = view.findViewById(R.id.recipeName)
            recipeLikes = view.findViewById(R.id.recipeLikes)
            recipeScore = view.findViewById(R.id.recipeScore)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_in_list, parent, false)
        return RecipeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setNewRecipes(newRecipes: List<Recipe>) {
        val diffCallback = RecipeListCallback(recipes, newRecipes)
        val diffCourses = DiffUtil.calculateDiff(diffCallback)
        recipes.clear()
        recipes.addAll(newRecipes)
        initialRecipes.addAll(newRecipes)
        diffCourses.dispatchUpdatesTo(this)
    }

    fun setFilteredRecipes(newRecipes: List<Recipe>){
        val diffCallback = RecipeListCallback(recipes, newRecipes)
        val diffCourses = DiffUtil.calculateDiff(diffCallback)
        recipes.clear()
        recipes.addAll(newRecipes)
        diffCourses.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.recipeName.text = recipes[position].name
        holder.recipeLikes.text = recipes[position].likes.toString()
        holder.recipeScore.text = recipes[position].score.toString()
        Picasso.get().load(recipes[position].image).into(holder.recipeImage)
    }

    fun getFilter(): Filter {
        return recipeFilter
    }

    private val recipeFilter = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList: ArrayList<Recipe> = ArrayList()
            if (p0.isNullOrEmpty()) {
                filteredList.addAll(initialRecipes)
            } else {
                val query = p0.toString().trim().lowercase(Locale.getDefault())
                initialRecipes.forEach {
                    if (it.name.lowercase(Locale.getDefault()).contains(query)) {
                        filteredList.add(it)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            if (p1?.values is ArrayList<*>) {
                setFilteredRecipes(p1.values as ArrayList<Recipe>)
            }
        }

    }
}