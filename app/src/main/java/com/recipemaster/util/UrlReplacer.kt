package com.recipemaster.util

class UrlReplacer (private val list: List<String>){

   fun replace() : List<String>{
        val result = mutableListOf<String>()
        for (item in list){
            result.add(item.replace(WRONG_URL, CORRECT_URL))
        }
        return result
    }

    companion object{
        const val WRONG_URL = "http://mooduplabs.com"
        const val CORRECT_URL = "https://moodup.team"
    }
}