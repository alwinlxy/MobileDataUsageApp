package com.example.android.mobiledatausage.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.android.mobiledatausage.database.AppDatabase
import com.example.android.mobiledatausage.database.DbAnuualMobileData
import com.example.android.mobiledatausage.model.AnnualMobileData
import com.example.android.mobiledatausage.repository.DataRepository
import kotlinx.coroutines.launch

class DataViewModel(app: Application) : AndroidViewModel(app) {

    //private val _recordList = MutableLiveData<List<DbAnuualMobileData>>()
    val recordList: LiveData<List<DbAnuualMobileData>>
        get() = dataRepository.records

    private val database = AppDatabase.getInstance(app)
    private val dataRepository = DataRepository(database)

    init {
        //get data from Repository
        viewModelScope.launch {
            dataRepository.getData()
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DataViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DataViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}