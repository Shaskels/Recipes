package com.example.recipes.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.text.Layout.Directions
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.presentation.MyRecipesViewModel
import com.example.recipes.presentation.RecipeListAdapter
import com.example.recipes.presentation.RecipeListViewModel
import com.google.android.material.progressindicator.LinearProgressIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    private lateinit var adapter: RecipeListAdapter
    private val viewModel: RecipeListViewModel by viewModels()
    private val myRecipesViewModel: MyRecipesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onResume() {
        (activity as MainActivity).supportActionBar?.title =  "Recipes"
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recipeList: RecyclerView = view.findViewById(R.id.recipe_list)
        val searchView: SearchView = view.findViewById(R.id.searchView)
        val prependProgress: LinearProgressIndicator = view.findViewById(R.id.prepend_progress)
        val appendProgress: LinearProgressIndicator = view.findViewById(R.id.append_progress)

       setupRecipesList(recipeList)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recipes.collectLatest {
                    if (viewModel.shouldScroll.value) {
                        recipeList.scrollToPosition(0)
                        viewModel.setScrolled()
                    }
                    adapter.submitData(it)
                }
            }
        }

        setupProgressBars(prependProgress, appendProgress)
        setupSearchView(searchView)
    }

    private fun setupRecipesList(recipeList: RecyclerView){
        adapter = RecipeListAdapter( onItemClick =  { recipe ->
            val action = RecipeListFragmentDirections.nextAction()
            action.selectedItem = recipe.idInApi
            findNavController().navigate(action)
        }, onLikeButtonClick = { recipe, isSelected ->
            if (isSelected){
                myRecipesViewModel.addLikedRecipe(recipe)
            } else {
                myRecipesViewModel.deleteLikedRecipe(recipe.idInApi)
            }
        })

        recipeList.adapter = adapter
        recipeList.layoutManager = LinearLayoutManager(context)
        recipeList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun setupProgressBars(prependProgress: LinearProgressIndicator, appendProgress: LinearProgressIndicator){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                adapter.loadStateFlow.collect {
                    prependProgress.isVisible = it.source.prepend is LoadState.Loading
                    appendProgress.isVisible = it.source.append is LoadState.Loading
                }
            }
        }
    }

    private fun setupSearchView(searchView: SearchView){
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