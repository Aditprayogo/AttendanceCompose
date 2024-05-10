package com.aditprayogo.core.data.repository.profile

import com.aditprayogo.core.data.model.firebase.UserProfileSnapshot
import com.aditprayogo.core.data.model.firebase.toUserProfile
import com.aditprayogo.core.entity.User
import com.aditprayogo.core.entity.UserProfile
import com.aditprayogo.core.utils.ResultState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : ProfileRepository {

    override fun getUserData() = User(
        username = auth.currentUser?.displayName ?: "",
        email = auth.currentUser?.email ?: ""
    )

    override fun getProfileData(): Flow<ResultState<UserProfile>> =
        callbackFlow {
            trySend(ResultState.Loading)

            val userProfileRef = firebaseDatabase.reference
                .child("profile")
                .child(auth.uid ?: "")

            val userProfileValueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userProfileSnapshot = snapshot.getValue<UserProfileSnapshot>()

                    if (userProfileSnapshot == null) {
                        trySend(ResultState.Error("Data Not Found"))
                    } else {
                        val userProfile = userProfileSnapshot.toUserProfile()
                        trySend(
                            ResultState.Success(
                                userProfile
                            )
                        )
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(ResultState.Error(error.message))
                }
            }

            userProfileRef.addValueEventListener(userProfileValueEventListener)

            awaitClose { userProfileRef.removeEventListener(userProfileValueEventListener) }
        }

}