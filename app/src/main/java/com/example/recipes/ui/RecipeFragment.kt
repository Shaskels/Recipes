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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.domain.Ingredient
import com.example.recipes.domain.Step
import com.example.recipes.presentation.RecipeListViewModel
import com.example.recipes.presentation.RecipeState
import com.example.recipes.presentation.ingredientList.IngredientsListAdapter
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import java.util.Locale


private const val SELECTED_ITEM = "selectedItem"

/**
 * A simple [Fragment] subclass.
 * Use the [RecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
        arguments?.let {
            selectedItemId = it.getInt(SELECTED_ITEM)
        }
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
            is RecipeState.Error -> Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
            is RecipeState.Loading -> Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT)
                .show()

            is RecipeState.Success -> {
                likes.text = state.recipe.likes.toString()
                score.text = "%.2f".format(Locale.ROOT, state.recipe.score)
                cookingTime.text = state.recipe.cookTime.toString()
                name.text = state.recipe.name
                Picasso.get().load(state.recipe.image).into(image)
                val uiModel = ArrayList<UiModel>()
                state.recipe.ingredients?.map { UiModel.IngredientItem(it) }
                    ?.let { uiModel.addAll(it) }
                state.recipe.instructions?.let { UiModel.StepsItem(it) }?.let { uiModel.add(it) }
                adapter.setNewItems(uiModel)
            }
        }
    }

    sealed class UiModel() {
        data class IngredientItem(val ingredient: Ingredient) : UiModel()
        data class StepsItem(val stepsItem: List<Step>) : UiModel()
    }

    companion object {

        const val RECIPE_FRAGMENT_IN_BACKSTACK = "recipeFragment"

        @JvmStatic
        fun newInstance(selectedItemId: Int) = RecipeFragment().apply {
            arguments = Bundle().apply {
                putInt(SELECTED_ITEM, selectedItemId)
            }
        }
    }
}