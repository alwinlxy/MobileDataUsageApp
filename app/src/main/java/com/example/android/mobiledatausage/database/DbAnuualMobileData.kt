package com.example.android.mobiledatausage.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "annual_mobiledata_table")
data class DbAnuualMobileData(
    @PrimaryKey val year: Int,
    val q1: Double,
    val q2: Double,
    val q3: Double,
    val q4: Double,
    val totalVolume: Double,
    val decrease: Boolean
)
