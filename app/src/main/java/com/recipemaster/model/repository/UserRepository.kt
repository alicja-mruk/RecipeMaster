package com.recipemaster.model.repository
import com.recipemaster.contract.HomeContract

interface UserRepository {
    fun requestUserData(onResponseCallback: HomeContract.OnResponseCallback)
}