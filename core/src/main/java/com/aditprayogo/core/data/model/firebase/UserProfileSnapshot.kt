package com.aditprayogo.core.data.model.firebase

import com.aditprayogo.core.entity.UserProfile
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class UserProfileSnapshot(
    val address: String? = null,
    val employeeNumber: String? = null,
    val jobTitle: String? = null
)

fun UserProfileSnapshot.toUserProfile(): UserProfile =
    UserProfile(
        address = address ?: "",
        employeeNumber = employeeNumber ?: "",
        jobTitle = jobTitle ?: ""
    )