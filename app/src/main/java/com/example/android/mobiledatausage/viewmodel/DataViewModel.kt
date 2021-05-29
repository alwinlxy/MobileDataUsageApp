package com.example.android.mobiledatausage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.mobiledatausage.database.DbAnuualMobileData
import com.example.android.mobiledatausage.model.AnnualMobileData

class DataViewModel : ViewModel() {

    private val _response = MutableLiveData<List<AnnualMobileData>>()
    val response: LiveData<List<AnnualMobileData>>
        get() = _response

    private val _recordList = MutableLiveData<List<DbAnuualMobileData>>()
    val recordList: LiveData<List<DbAnuualMobileData>>
        get() = _recordList

    init {
        getMockData()
    }

    /*
    For simulating a data source retrieval
     */
    private fun getMockData() {
        //from network
        val mockList = mutableListOf<AnnualMobileData>()
        mockList.add(AnnualMobileData(2005, 0.123))
        mockList.add(AnnualMobileData(2006, 0.456))
        mockList.add(AnnualMobileData(2007, 0.789))
        mockList.add(AnnualMobileData(2008, 0.321))
        mockList.add(AnnualMobileData(2009, 0.654))
        mockList.add(AnnualMobileData(2010, 0.987))

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

        _recordList.value = newList

    }
}