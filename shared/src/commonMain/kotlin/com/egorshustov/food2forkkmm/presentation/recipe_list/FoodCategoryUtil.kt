package com.egorshustov.food2forkkmm.presentation.recipe_list

object FoodCategoryUtil {

    fun getAllFoodCategories(): List<FoodCategory> = listOf(
        FoodCategory.ERROR,
        FoodCategory.CHICKEN,
        FoodCategory.BEEF,
        FoodCategory.SOUP,
        FoodCategory.DESSERT,
        FoodCategory.VEGETARIAN,
        FoodCategory.MILK,
        FoodCategory.VEGAN,
        FoodCategory.PIZZA,
        FoodCategory.DONUT
    )

    fun getFoodCategory(value: String): FoodCategory? {
        val map = FoodCategory.values().associateBy(FoodCategory::value)
        return map[value]
    }
}