package com.example.recipes.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recipes.domain.Recipe
import com.example.recipes.domain.usecases.GetAllRecipes
import com.example.recipes.domain.usecases.GetSelectedRecipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val getAllRecipes: GetAllRecipes,
    private val getSelectedRecipe: GetSelectedRecipe
) : ViewModel() {

    private val _selectedRecipe = MutableLiveData<RecipeState>()
    val selectedRecipe = _selectedRecipe

    private val _search = MutableStateFlow("")
    private val search = _search.asStateFlow()

    var recipes: Flow<PagingData<Recipe>> =
        search.debounce(900).flatMapLatest { query ->
            Log.i("ViewModel", query)
            getAllRecipes(query).cachedIn(viewModelScope)
        }

    fun onQueryChanged(query: String) {
        _search.value = query
    }

    fun getRecipe(id: Int){
        _selectedRecipe.value = RecipeState.Loading
        viewModelScope.launch {
            try {
                val res = getSelectedRecipe(id)
                _selectedRecipe.value = RecipeState.Success(res)
            } catch (e : Exception){
                Log.e("ViewModel", e.message.toString())
                _selectedRecipe.value = RecipeState.Error
            }
        }
    }


}