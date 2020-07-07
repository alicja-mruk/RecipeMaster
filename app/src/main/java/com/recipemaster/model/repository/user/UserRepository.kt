package com.recipemaster.model.repository.user
import com.recipemaster.contract.HomeContract

interface UserRepository {
    fun requestUserData(onResponseCallback: HomeContract.OnResponseCallback)
}