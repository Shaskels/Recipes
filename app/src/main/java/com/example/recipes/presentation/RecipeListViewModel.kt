package com.example.recipes.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipes.domain.usecases.GetAllRecipes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(private val getAllRecipes: GetAllRecipes) : ViewModel() {
    private val _recipesListState = MutableLiveData<RecipeListState>()
    val recipeListState: LiveData<RecipeListState> = _recipesListState

    fun getRecipesList(){
        viewModelScope.launch {
            _recipesListState.value = RecipeListState.Loading
            try {
                val result = getAllRecipes()
                _recipesListState.value = RecipeListState.Success(result)

            } catch (e: Exception) {
                Log.e("RecipeListViewModel", e.message.toString())
                _recipesListState.value = RecipeListState.Error
            }
        }
    }
}