package com.example.android.mobiledatausage.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.mobiledatausage.database.AppDatabase
import com.example.android.mobiledatausage.database.DbAnuualMobileData
import com.example.android.mobiledatausage.model.AnnualMobileData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataRepository(private val appDB: AppDatabase) {

    private val _records = MutableLiveData<List<DbAnuualMobileData>>()

    val records: LiveData<List<DbAnuualMobileData>>
        get() = _records

    suspend fun getData() {
        withContext(Dispatchers.IO) {
            val dbRecordList = appDB.dbDAO.getAllRecords()
            if(dbRecordList.size > 0) {
                _records.postValue(dbRecordList)
            } else {
                //TODO getDataFromRemote()
                getDataFromLocalString()
            }

        }
    }

    //TODO complete implementation
    private suspend fun getDataFromRemote() {

    }

    private suspend fun getDataFromLocalString() {
        withContext(Dispatchers.IO) {
            try {

                //simulated data
                val mockList = mutableListOf<AnnualMobileData>()
                mockList.add(AnnualMobileData(2008, 0.123))
                mockList.add(AnnualMobileData(2009, 0.456))
                mockList.add(AnnualMobileData(2010, 0.789))
                mockList.add(AnnualMobileData(2011, 0.321))
                mockList.add(AnnualMobileData(2012, 0.654))

                //transform to database model
                val newList = mockList.map {
                    DbAnuualMobileData(
                        it.year,
                        0.0,
                        0.0,
                        0.0,
                        0.0,
                        it.volume,
                        it.hasDecrease()
                    )
                }

                appDB.dbDAO.insertAll(*newList.toTypedArray())
                _records.postValue(appDB.dbDAO.getAllRecords())
            } catch (t: Throwable) {
                Log.e("getDataFromRemote", "Error getting remote data")
            }
        }

    }
}