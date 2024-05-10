package com.aditprayogo.core.data.repository.office

import com.aditprayogo.core.data.model.firebase.OfficeSnapshot
import com.aditprayogo.core.data.model.firebase.toOffice
import com.aditprayogo.core.entity.Office
import com.aditprayogo.core.utils.ResultState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfficeRepositoryImpl @Inject constructor(
    firebaseDatabase: FirebaseDatabase
) : OfficeRepository {

    private val officesReference = firebaseDatabase.getReference("offices")

    override fun getOffices(): Flow<ResultState<List<Office>>> =
        callbackFlow {

            val officesValueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val officesSnapshot = snapshot.children.mapNotNull {
                        it.getValue(OfficeSnapshot::class.java)
                    }

                    val offices = officesSnapshot.map { it.toOffice() }

                    trySend(
                        ResultState.Success(
                            offices
                        )
                    )
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(ResultState.Error(error.message))
                }
            }

            officesReference.addValueEventListener(officesValueEventListener)

            awaitClose {
                officesReference.removeEventListener(officesValueEventListener)
            }
        }

}