package com.example.android.mobiledatausage.util

import com.example.android.mobiledatausage.database.DbAnuualMobileData
import com.example.android.mobiledatausage.model.AnnualMobileData
import org.json.JSONArray
import org.json.JSONObject

object DataParser {

    //parse the json response to get the records JSONArray
    fun getRecordsArray(input: String) : JSONArray? {

        var resultArr: JSONArray? = null
        kotlin.runCatching {
            val response = JSONObject(input)
            val result: JSONObject
            if (response.has("result")) {
                result = response.getJSONObject("result")
                if (result.has("records")) {
                    resultArr = result.getJSONArray("records")
                }
            }
        }

        return resultArr
    }


    //split the quarter string to get year and quarter
    fun splitYearQuarter(quarterStr: String) : Pair<Int, Int> {

        var result = Pair(0, 0)
        val arr = quarterStr.split("-Q")
        val year: Int?
        val quarter: Int?
        if (arr.size == 2) {
            year = arr.get(0).toIntOrNull()
            quarter = arr.get(1).toIntOrNull()
            if (year != null && quarter != null) {
                if (year >= 2008 && year <= 2018 && quarter >= 1 && quarter <= 4) {
                    result = Pair(year, quarter)
                }
            }
        }
        return result
    }

    //construct model from JSONObject
    fun getAnnualMobileDataFromJsonObject(obj: JSONObject) : Pair<AnnualMobileData?, Int>{

        var result: Pair<AnnualMobileData?, Int> = Pair(null, 0)
        val volume: Double
        val quarterStr: String
        val tempObj: AnnualMobileData
        val pairResult: Pair<Int, Int>
        if(obj.has("volume_of_mobile_data") && obj.has("quarter")) {
            volume = obj.getDouble("volume_of_mobile_data")
            quarterStr = obj.getString("quarter")
            pairResult = splitYearQuarter(quarterStr)
            if(pairResult.first >0 && pairResult.second > 0) {
                tempObj = AnnualMobileData(pairResult.first)
                tempObj.setQuarterVolume(pairResult.second, volume)
                result = Pair(tempObj, pairResult.second)
            }
        }
        return result
    }

    // transform model to dbModel
    fun transformModelToDbModel(modelList: List<AnnualMobileData>) : List<DbAnuualMobileData> {
        return modelList.map {
            DbAnuualMobileData(
                it.year,
                it.getQuarter(1),
                it.getQuarter(2),
                it.getQuarter(3),
                it.getQuarter(4),
                it.getAnnualDataVol(),
                it.hasDecrease()
            )
        }
    }

    fun parseResponse(response: String) : List<DbAnuualMobileData> {

        val recordMap = HashMap<Int, AnnualMobileData>()
        val records = getRecordsArray(response)
        val recordList = mutableListOf<DbAnuualMobileData>()

        //parse records JSONArray
        if (records != null) {
            var temp: JSONObject
            for (x in 0..records.length() - 1) {
                temp = records.getJSONObject(x)

                //parse year and quarter
                val (tempAnnualMobileData, quarter) = getAnnualMobileDataFromJsonObject(temp)
                if(tempAnnualMobileData != null) {
                    if (recordMap.containsKey(tempAnnualMobileData.year)) {
                        recordMap.get(tempAnnualMobileData.year)?.setQuarterVolume(quarter, tempAnnualMobileData.getQuarter(quarter))
                    } else {
                        recordMap.put(tempAnnualMobileData.year, tempAnnualMobileData)
                    }
                }
            }
            val sortedMapList = recordMap.toSortedMap().values.toList()
            recordList.addAll(transformModelToDbModel(sortedMapList))
        }

        return recordList
    }
}