package com.aditprayogo.core.data.repository.auth

import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.aditprayogo.core.data.state.AuthState
import com.aditprayogo.core.utils.ResultState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override fun currentAuthState(): Flow<AuthState> = callbackFlow {

        val listener = AuthStateListener { p0 ->
            p0.currentUser?.let {
                trySend(AuthState.SignedIn)
            } ?: trySend(AuthState.SignedOut)
        }

        auth.addAuthStateListener(listener)

        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override fun register(
        email: String,
        password: String,
        fullName: String
    ): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        val profileUpdatesListener = OnCompleteListener<Void> { task ->
            if (task.isSuccessful) {
                trySend(ResultState.Success("Register Success"))
            } else {
                trySend(
                    ResultState.Error(
                        task.exception?.message ?: "Register Failed"
                    )
                )
            }
        }

        val registerListener = OnCompleteListener<AuthResult> { task ->
            if (task.isSuccessful) {
                val profileUpdates = userProfileChangeRequest {
                    displayName = fullName
                }

                auth.currentUser!!.updateProfile(profileUpdates)
                    .addOnCompleteListener(profileUpdatesListener)
            } else {
                trySend(
                    ResultState.Error(
                        task.exception?.message ?: "Register Failed"
                    )
                )
            }
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(registerListener)

        awaitClose { }
    }

    override fun login(
        email: String,
        password: String,
    ): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        val listener = OnCompleteListener<AuthResult> {
            if (it.isSuccessful) {
                trySend(ResultState.Success("Login Success"))
            } else {
                trySend(
                    ResultState.Error(
                        it.exception?.message ?: "Login Failed"
                    )
                )
            }
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(listener)

        awaitClose { }
    }

    override fun signOut() {
        auth.signOut()
    }
}