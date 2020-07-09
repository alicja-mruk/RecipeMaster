package com.recipemaster.model.network.request.user
import com.recipemaster.contract.HomeContract

interface IUserClient {
    fun requestUserData(onResponseCallback: HomeContract.OnResponseCallback)
}