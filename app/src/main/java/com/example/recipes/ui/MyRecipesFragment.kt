package com.example.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes.R
import com.example.recipes.presentation.MyRecipesState
import com.example.recipes.presentation.MyRecipesViewModel
import com.example.recipes.presentation.myRecipesList.MyRecipesListAdapter

class MyRecipesFragment : Fragment() {

    private lateinit var adapter: MyRecipesListAdapter
    private val viewModel: MyRecipesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.show()
        (activity as MainActivity).supportActionBar?.title =  getString(R.string.my_recipes_list_title)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val myRecipeList: RecyclerView = view.findViewById(R.id.my_recipes_list)
        viewModel.myRecipes.observe(viewLifecycleOwner) { state ->
           renderState(state)
        }

        adapter = MyRecipesListAdapter( onItemClick =  { recipe ->
            val action = MyRecipesFragmentDirections.nextAction()
            action.selectedItem = recipe.idInApi
            findNavController().navigate(action)
        }, onLikeButtonClick = { recipe, isSelected ->
            if (isSelected){
                viewModel.addLikedRecipe(recipe)
            } else {
                viewModel.deleteLikedRecipe(recipe.idInApi)
            }
        })
        myRecipeList.adapter = adapter
        myRecipeList.layoutManager = LinearLayoutManager(context)
        myRecipeList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        viewModel.getRecipes()
    }

    private fun renderState(state: MyRecipesState){
        when(state){
            is MyRecipesState.Error -> Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            is MyRecipesState.Updated -> adapter.setNewItems(state.recipes)
        }
    }

}