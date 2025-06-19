package com.example.recipes.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.recipes.domain.Recipe
import com.example.recipes.domain.usecases.GetAllRecipes
import com.example.recipes.domain.usecases.GetMyRecipes
import com.example.recipes.domain.usecases.GetSelectedRecipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val getAllRecipes: GetAllRecipes,
    private val getSelectedRecipe: GetSelectedRecipe,
    private val getMyRecipes: GetMyRecipes,
) : ViewModel() {

    private val _selectedRecipe = MutableLiveData<RecipeState>()
    val selectedRecipe: LiveData<RecipeState> = _selectedRecipe

    private val _shouldScroll = MutableStateFlow(false)
    val shouldScroll = _shouldScroll.asStateFlow()

    private val _search = MutableStateFlow("")
    private val search = _search.asStateFlow()

    var recipes: Flow<PagingData<Recipe>> =
        search.debounce(900).flatMapLatest { query ->
            Log.i("ViewModel", query)
            val job = viewModelScope.async {
                getMyRecipes()
            }
            val res = job.await()
            getAllRecipes(query).map { pagingData ->
                pagingData.map {
                    if (res.find { p -> p.idInApi == it.idInApi } != null)
                        it.isLiked = true
                    it
                }
            }.cachedIn(viewModelScope)
        }

    fun setScrolled() {
        _shouldScroll.value = false
    }

    fun onQueryChanged(query: String) {
        _search.value = query
        _shouldScroll.value = true
    }

    fun getRecipe(id: Int) {
        _selectedRecipe.value = RecipeState.Loading
        viewModelScope.launch {
            try {
                val res = getSelectedRecipe(id)
                _selectedRecipe.value = RecipeState.Success(res)
            } catch (e: Exception) {
                Log.e("ViewModel", e.message.toString())
                _selectedRecipe.value = RecipeState.Error
            }
        }
    }

}