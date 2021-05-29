package com.example.android.mobiledatausage.model

data class AnnualMobileData(val year:Int, val volume: Double) {

    //for the 4 quarters of the year
    private val dataVolArr = Array<Double>(4){0.0}

    fun getAnnualDataVol() : Double = dataVolArr.sum()

    //if any of the quarter has a decrease over the previous quarter
    fun hasDecrease() : Boolean {
        var result = false
        var temp = dataVolArr[0]
        for(x in 1..3) {
            if(dataVolArr[x] == 0.0)
                continue

            if(dataVolArr[x] < temp) {
                result = true
                break
            }
            temp = dataVolArr[x]

        }

        return result
    }

    //set the data volume of a quarter
    fun setQuarterVolume(quarter: Int, dataVolume: Double) : Boolean {

        if(quarter < 0 || quarter > 4) { return false }

        dataVolArr[quarter-1] = dataVolume
        return true
    }



}
