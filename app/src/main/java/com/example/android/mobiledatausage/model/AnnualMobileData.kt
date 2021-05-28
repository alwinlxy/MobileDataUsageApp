package com.example.android.mobiledatausage.model

data class AnnualMobileData(val year:Int, val volume: Double) {

    /*
    TODO check if quarter's data volume shows decrease compared to previous quarter
     */
    fun hasDecrease() : Boolean {
        return true
    }

}
