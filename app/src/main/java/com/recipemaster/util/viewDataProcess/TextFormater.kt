package com.recipemaster.util.viewDataProcess

object TextFormater{

    fun processIngredients(ingredients: List<String>): String {
        return buildString{
            for (item in ingredients){
                append("- ")
                append(item)
                append("\n")
            }
        }
    }

   fun processPreparing(preparing: List<String>): String {
        return buildString{
            preparing.forEachIndexed { index, item ->
                append(index+1)
                append(". ")
                append(item)
                append("\n\n")
            }
        }
    }
}