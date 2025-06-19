package com.example.recipes.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipes.domain.Recipe
import com.example.recipes.domain.usecases.AddMyRecipe
import com.example.recipes.domain.usecases.DeleteMyRecipe
import com.example.recipes.domain.usecases.GetMyRecipes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyRecipesViewModel @Inject constructor(
    private val deleteMyRecipe: DeleteMyRecipe,
    private val addMyRecipe: AddMyRecipe,
    private val getMyRecipes: GetMyRecipes,
) : ViewModel() {

    private val _myRecipes = MutableLiveData<MyRecipesState>()
    val myRecipes: LiveData<MyRecipesState> = _myRecipes

    fun deleteLikedRecipe(id: Int) {
        viewModelScope.launch {
            try {
                deleteMyRecipe(id)
                _myRecipes.value =
                    MyRecipesState.Updated(getMyRecipes().map {
                        it.isLiked = true
                        it
                    })
            } catch (e: Exception) {
                Log.d("MyRecipesViewModel", e.message.toString())
                _myRecipes.value = MyRecipesState.Error
            }
        }
    }

    fun addLikedRecipe(recipe: Recipe) {
        viewModelScope.launch {
            try {
                addMyRecipe(recipe)
                _myRecipes.value = MyRecipesState.Updated(getMyRecipes().map {
                    it.isLiked = true
                    it
                })
            } catch (e: Exception) {
                Log.d("MyRecipesViewModel", e.message.toString())
                _myRecipes.value = MyRecipesState.Updated(getMyRecipes())
            }
        }
    }

    fun getRecipes() {
        viewModelScope.launch {
            try {
                val res = getMyRecipes().map {
                    it.isLiked = true
                    it
                }
                _myRecipes.value = MyRecipesState.Updated(res)
            } catch (e: Exception) {
                Log.d("MyRecipesViewModel", e.message.toString())
                _myRecipes.value = MyRecipesState.Updated(getMyRecipes())
            }
        }
    }
}