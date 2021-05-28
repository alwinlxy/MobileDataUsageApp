package com.example.android.mobiledatausage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.mobiledatausage.model.AnnualMobileData

class DataViewModel : ViewModel() {

    private val _response = MutableLiveData<List<AnnualMobileData>>()
    val response: LiveData<List<AnnualMobileData>>
        get() = _response

    init {
        getMockData()
    }

    /*
    For simulating a data source retrieval
     */
    private fun getMockData() {
        val mockList = mutableListOf<AnnualMobileData>()
        mockList.add(AnnualMobileData(2005, 0.123))
        mockList.add(AnnualMobileData(2006, 0.456))
        mockList.add(AnnualMobileData(2007, 0.789))

        _response.value = mockList
    }
}