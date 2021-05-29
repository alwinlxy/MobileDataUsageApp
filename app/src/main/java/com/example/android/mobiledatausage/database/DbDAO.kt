package com.example.android.mobiledatausage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DbDAO {

    //for insert of new record
    @Insert
    fun insert(record: DbAnuualMobileData)

    //for insert of mutiple records
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg record: DbAnuualMobileData)

    //to clear all records
    @Query(value = "DELETE FROM annual_mobiledata_table")
    fun clear()

    //to get all records in db
    @Query(value = "SELECT * FROM annual_mobiledata_table ORDER BY year")
    fun getAllRecords(): List<DbAnuualMobileData>
}