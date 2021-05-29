package com.example.android.mobiledatausage.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.mobiledatausage.database.AppDatabase
import com.example.android.mobiledatausage.database.DbAnuualMobileData
import com.example.android.mobiledatausage.model.AnnualMobileData
import com.example.android.mobiledatausage.network.DataApi
import com.example.android.mobiledatausage.util.DataParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataRepository(private val appDB: AppDatabase) {

    private val _records = MutableLiveData<List<DbAnuualMobileData>>()
    val records: LiveData<List<DbAnuualMobileData>>
        get() = _records

    suspend fun getData() {
        withContext(Dispatchers.IO) {
            val dbRecordList = appDB.dbDAO.getAllRecords()
            if(dbRecordList.isNotEmpty()) {
                _records.postValue(dbRecordList)
            } else {
                getDataFromRemote()
                //getDataFromLocalString()
            }

        }
    }

    //TODO complete implementation
    private suspend fun getDataFromRemote() {
        withContext(Dispatchers.IO) {
            try {
                val response = DataApi.retrofitService.getData()
                val recordList = DataParser.parseResponse(response)
                appDB.dbDAO.insertAll(*recordList.toTypedArray())
                _records.postValue(appDB.dbDAO.getAllRecords())
            } catch (e: Exception) {
                Log.e("getDataFromRemote", "Error getting remote data")
            }

        }
    }

    private suspend fun getDataFromLocalString() {
        withContext(Dispatchers.IO) {
            try {

                val str = "{\"help\": \"https://data.gov.sg/api/3/action/help_show?name=datastore_search\", \"success\": true, \"result\": {\"resource_id\": \"a807b7ab-6cad-4aa6-87d0-e283a7353a0f\", \"fields\": [{\"type\": \"int4\", \"id\": \"_id\"}, {\"type\": \"text\", \"id\": \"quarter\"}, {\"type\": \"numeric\", \"id\": \"volume_of_mobile_data\"}, {\"type\": \"int8\", \"id\": \"_full_count\"}, {\"type\": \"float4\", \"id\": \"rank\"}], \"q\": \"2008\", \"records\": [{\"volume_of_mobile_data\": \"0.171586\", \"quarter\": \"2008-Q1\", \"_id\": 15, \"_full_count\": \"4\", \"rank\": 0.0573088}, {\"volume_of_mobile_data\": \"0.248899\", \"quarter\": \"2008-Q2\", \"_id\": 16, \"_full_count\": \"4\", \"rank\": 0.0573088}, {\"volume_of_mobile_data\": \"0.439655\", \"quarter\": \"2008-Q3\", \"_id\": 17, \"_full_count\": \"4\", \"rank\": 0.0573088}, {\"volume_of_mobile_data\": \"0.683579\", \"quarter\": \"2008-Q4\", \"_id\": 18, \"_full_count\": \"4\", \"rank\": 0.0573088}], \"_links\": {\"start\": \"/api/action/datastore_search?q=2008&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f\", \"next\": \"/api/action/datastore_search?q=2008&offset=100&resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f\"}, \"total\": 4}}"
                val recordList = DataParser.parseResponse(str)
                appDB.dbDAO.insertAll(*recordList.toTypedArray())
                _records.postValue(appDB.dbDAO.getAllRecords())
            } catch (t: Throwable) {
                Log.e("getDataFromRemote", "Error getting remote data")
            }
        }

    }
}