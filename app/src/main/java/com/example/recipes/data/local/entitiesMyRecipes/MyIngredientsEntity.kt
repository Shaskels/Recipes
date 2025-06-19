package com.example.recipes.data.local.entitiesMyRecipes

//import androidx.room.ColumnInfo
//import androidx.room.Entity
//import androidx.room.ForeignKey
//import androidx.room.PrimaryKey
//
//@Entity(tableName = "myIngredients",
//    foreignKeys = [ForeignKey(
//        entity = MyRecipesEntity::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("recipeId"),
//        onUpdate = ForeignKey.CASCADE,
//        onDelete = ForeignKey.CASCADE,)]
//    )
//data class MyIngredientsEntity (
//    @PrimaryKey
//    @ColumnInfo("id")
//    val id: Int,
//    @ColumnInfo("recipeId", index = true)
//    val recipeId: Int,
//    @ColumnInfo("name")
//    val name: String,
//    @ColumnInfo("amount")
//    val amount: Double,
//    @ColumnInfo("unit")
//    val unit: String,
//)