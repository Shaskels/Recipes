package com.example.recipes.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.presentation.RecipeListAdapter
import com.example.recipes.presentation.RecipeListState
import com.example.recipes.presentation.RecipeListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    companion object {
        fun newInstance() = RecipeListFragment()
    }

    private lateinit var adapter: RecipeListAdapter
    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.recipeListState.observe(viewLifecycleOwner) { result ->
            renderState(result)
        }
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recipeList: RecyclerView = view.findViewById(R.id.recipe_list)
        val searchView: SearchView = view.findViewById(R.id.searchView)

        adapter = RecipeListAdapter(ArrayList())
        recipeList.adapter = adapter
        recipeList.layoutManager = LinearLayoutManager(context)
        recipeList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        viewModel.getRecipesList()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.getFilter().filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.getFilter().filter(newText);
                return true
            }

        })
    }

    private fun renderState(state: RecipeListState) {
        when (state) {
            is RecipeListState.Loading -> Toast.makeText(context, "Loading..", Toast.LENGTH_SHORT)
                .show()

            is RecipeListState.Error -> Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            is RecipeListState.Success -> {
                adapter.setNewRecipes(state.recipes)
            }
        }
    }
}