package com.aditprayogo.core.data.repository.profile

import com.aditprayogo.core.entity.User
import com.aditprayogo.core.entity.UserProfile
import com.aditprayogo.core.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getUserData(): User

    fun getProfileData(): Flow<ResultState<UserProfile>>

}