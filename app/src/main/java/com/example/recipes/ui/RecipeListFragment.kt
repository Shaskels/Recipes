package com.example.recipes.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.presentation.RecipeListAdapter
import com.example.recipes.presentation.RecipeListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recipeList: RecyclerView = view.findViewById(R.id.recipe_list)
        val searchView: SearchView = view.findViewById(R.id.searchView)

        adapter = RecipeListAdapter() { recipe ->
            parentFragmentManager.beginTransaction().apply {
                add(R.id.fragment_container, RecipeFragment.newInstance(recipe.idInApi))
                addToBackStack(RecipeFragment.RECIPE_FRAGMENT_IN_BACKSTACK)
                commit()
            }
        }
        recipeList.adapter = adapter
        recipeList.layoutManager = LinearLayoutManager(context)
        recipeList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recipes.collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.onQueryChanged(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onQueryChanged(newText ?: "")
                return true
            }

        })
    }

}