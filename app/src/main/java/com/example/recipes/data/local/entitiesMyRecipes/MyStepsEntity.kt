package com.example.recipes.data.local.entitiesMyRecipes

//import androidx.room.ColumnInfo
//import androidx.room.Entity
//import androidx.room.ForeignKey
//import androidx.room.PrimaryKey
//
//@Entity(
//    tableName = "mySteps",
//    foreignKeys = [ForeignKey(entity = MyRecipesEntity::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("recipeId"),
//        onUpdate = ForeignKey.CASCADE,
//        onDelete = ForeignKey.CASCADE,
//    )]
//)
//data class MyStepsEntity(
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo("id")
//    val id: Int = 0,
//    @ColumnInfo("recipeId", index = true)
//    val recipeId: Int,
//    @ColumnInfo("name")
//    val number: Int,
//    @ColumnInfo("step")
//    val step: String,
//)