package com.aditprayogo.core.data.repository.attendance

import com.aditprayogo.core.data.model.firebase.AttendanceRecordSnapshot
import com.aditprayogo.core.data.model.firebase.toOffice
import com.aditprayogo.core.data.paging.AttendanceRecordsPagingSource
import com.aditprayogo.core.data.state.AttendanceState
import com.aditprayogo.core.data.state.TodayAttendanceState
import com.aditprayogo.core.entity.Office
import com.aditprayogo.core.utils.CalendarUtils
import com.aditprayogo.core.utils.ResultState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class AttendanceRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val auth: FirebaseAuth
) : AttendanceRepository {

    override fun checkTodayAttendanceState(): Flow<ResultState<TodayAttendanceState>> =
        callbackFlow {
            trySend(ResultState.Loading)

            val userAttendanceReference = firebaseDatabase
                .getReference("attendance/${auth.currentUser?.uid}")
                .limitToLast(1)

            val todayAttendanceStateValueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val attendanceRecordSnapshots = snapshot.children.mapNotNull {
                        it.getValue(AttendanceRecordSnapshot::class.java)
                    }

                    if (attendanceRecordSnapshots.isEmpty()) {
                        trySend(
                            ResultState.Success(
                                TodayAttendanceState.NoRecord
                            )
                        )
                    } else {
                        val record = attendanceRecordSnapshots[0]

                        if (record.date == CalendarUtils.currentDate) {
                            if (record.status == AttendanceState.CheckIn.value) {
                                val currentOffice = record.toOffice()
                                trySend(
                                    ResultState.Success(
                                        TodayAttendanceState.CheckedIn(
                                            currentOffice
                                        )
                                    )
                                )
                            } else {
                                trySend(
                                    ResultState.Success(
                                        TodayAttendanceState.CheckedOut
                                    )
                                )
                            }
                        } else {
                            trySend(
                                ResultState.Success(
                                    TodayAttendanceState.NoRecord
                                )
                            )
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(ResultState.Error(error.message))
                }
            }

            userAttendanceReference.addValueEventListener(todayAttendanceStateValueEventListener)

            awaitClose {
                userAttendanceReference.removeEventListener(todayAttendanceStateValueEventListener)
            }
        }

    override fun recordAttendance(
        office: Office,
        attendanceState: AttendanceState
    ): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        val attendanceRecordSnapshot =
            AttendanceRecordSnapshot(
                status = attendanceState.value,
                officeId = office.id,
                address = office.address,
                officeImageUrl = office.imageUrl,
                officeName = office.name,
                date = CalendarUtils.currentDate,
                hour = CalendarUtils.currentHour
            )

        val userAttendanceReference = firebaseDatabase
            .getReference("attendance")
            .child(auth.currentUser?.uid ?: "")

        userAttendanceReference.push().setValue(attendanceRecordSnapshot)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(ResultState.Success("${attendanceState.value} Success"))
                } else {
                    trySend(
                        ResultState.Error(
                            task.exception?.message ?: "${attendanceState.value} Failed"
                        )
                    )
                }
            }
            .addOnFailureListener {
                trySend(
                    ResultState.Error(
                        it.message ?: "${attendanceState.value} Failed"
                    )
                )
            }

        awaitClose { }
    }

    override fun getAttendanceRecordsPagingSource(
        startDate: String,
        endDate: String
    ): AttendanceRecordsPagingSource = AttendanceRecordsPagingSource(
        db = firebaseDatabase.reference,
        startDate = startDate,
        endDate = endDate,
        userUniqueId = auth.currentUser?.uid ?: ""
    )

}