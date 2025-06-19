package com.example.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.Ingredient
import com.example.recipes.domain.Recipe
import com.example.recipes.domain.Step
import com.example.recipes.presentation.RecipeListViewModel
import com.example.recipes.presentation.RecipeState
import com.example.recipes.presentation.ingredientList.IngredientsListAdapter
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import java.util.Locale

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    private var selectedItemId: Int? = null
    private val viewModel: RecipeListViewModel by viewModels()
    private lateinit var adapter: IngredientsListAdapter
    private lateinit var likes: Chip
    private lateinit var score: Chip
    private lateinit var cookingTime: Chip
    private lateinit var name: TextView
    private lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.title = "Recipes"
        val safeArgs: RecipeFragmentArgs by navArgs()
        selectedItemId = safeArgs.selectedItem
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.selectedRecipe.observe(viewLifecycleOwner) { state ->
            renderState(state)
        }
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.ingredientList)
        likes = view.findViewById(R.id.likes)
        score = view.findViewById(R.id.score)
        cookingTime = view.findViewById(R.id.time)
        name = view.findViewById(R.id.recipeName)
        image = view.findViewById(R.id.recipeImage)

        adapter = IngredientsListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        if (selectedItemId != null)
            viewModel.getRecipe(selectedItemId!!)

    }

    private fun renderState(state: RecipeState) {
        when (state) {
            is RecipeState.Error -> Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            is RecipeState.Loading -> Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT)
                .show()

            is RecipeState.Success -> {
                setUi(state.recipe)
            }
        }
    }

    private fun setUi(recipe: Recipe) {
        likes.text = recipe.likes.toString()
        score.text = "%.2f".format(Locale.ROOT, recipe.score)
        cookingTime.text = recipe.cookTime.toString()
        name.text = recipe.name
        Picasso.get().load(recipe.image).placeholder(R.drawable.nophoto).error(R.drawable.nophoto)
            .into(image)
        val uiModel = ArrayList<UiModel>()
        recipe.ingredients?.map { UiModel.IngredientItem(it) }
            ?.let { uiModel.addAll(it) }
        recipe.instructions?.let { UiModel.StepsItem(it) }?.let { uiModel.add(it) }
        adapter.setNewItems(uiModel)
    }

    sealed class UiModel() {
        data class IngredientItem(val ingredient: Ingredient) : UiModel()
        data class StepsItem(val stepsItem: List<Step>) : UiModel()
    }
}