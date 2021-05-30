package com.example.android.mobiledatausage.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.mobiledatausage.database.AppDatabase
import com.example.android.mobiledatausage.database.DbAnuualMobileData
import com.example.android.mobiledatausage.network.DataApi
import com.example.android.mobiledatausage.util.DataParser
import com.example.android.mobiledatausage.util.ERROR
import com.example.android.mobiledatausage.util.LOADING
import com.example.android.mobiledatausage.util.SUCCESS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataRepository(private val appDB: AppDatabase) {

    private val _records = MutableLiveData<List<DbAnuualMobileData>>()
    val records: LiveData<List<DbAnuualMobileData>>
        get() = _records

    private val _status = MutableLiveData<Int>()
    val status: LiveData<Int>
        get() = _status

    suspend fun getData() {
        _status.postValue(LOADING)
        withContext(Dispatchers.IO) {
            val dbRecordList = appDB.dbDAO.getAllRecords()
            if(dbRecordList.isNotEmpty()) {
                _records.postValue(dbRecordList)
                _status.postValue(SUCCESS)
            } else {
                getDataFromRemote()
            }

        }
    }

    //TODO complete implementation
    private suspend fun getDataFromRemote() {
        withContext(Dispatchers.IO) {
            try {
                val response = DataApi.retrofitService.getData()
                if (response.isSuccessful) {
                    val recordList = DataParser.parseResponse(response.body().toString())
                    appDB.dbDAO.insertAll(*recordList.toTypedArray())
                    _records.postValue(appDB.dbDAO.getAllRecords())
                    _status.postValue(SUCCESS)
                } else {
                    //response error
                    _status.postValue(ERROR)
                }
            } catch (e: Exception) {
                _status.postValue(ERROR)
            }
        }
    }
}