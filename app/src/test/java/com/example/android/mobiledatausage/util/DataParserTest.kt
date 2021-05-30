package com.example.android.mobiledatausage.util

import com.example.android.mobiledatausage.model.AnnualMobileData
import com.example.android.mobiledatausage.repository.reponse_invalid_1
import com.example.android.mobiledatausage.repository.reponse_invalid_2
import com.example.android.mobiledatausage.repository.reponse_invalid_3
import com.example.android.mobiledatausage.repository.response_valid_2008
import org.junit.Assert.*
import org.junit.Test

class DataParserTest {
    @Test
    fun getRecordsArray_NonJsonString() {
        val input = reponse_invalid_1
        val output = DataParser.getRecordsArray(input)
        assertNull(output)

    }

    @Test
    fun getRecordsArray_MissingResultJsonObject() {
        val input = reponse_invalid_2
        val output = DataParser.getRecordsArray(input)
        assertNull(output)
    }

    @Test
    fun getRecordsArray_MissingRecordsJsonArray() {
        val input: String  = reponse_invalid_3
        val output = DataParser.getRecordsArray(input)
        assertNull(output)
    }

    @Test
    fun getRecordsArray_ValidJsonReponse() {
        val input: String = response_valid_2008
        val output = DataParser.getRecordsArray(input)
        assertNotNull(output)
    }

    @Test
    fun splitYearQuarter_InvalidInput() {
        val input1 ="2008"
        val (year1, quarter1) = DataParser.splitYearQuarter(input1)
        assertEquals(0, year1)
        assertEquals(0, quarter1)

        val input2 ="2008-Q1-Q1"
        val (year2, quarter2) = DataParser.splitYearQuarter(input2)
        assertEquals(0, year2)
        assertEquals(0, quarter2)

    }

    @Test
    fun splitYearQuarter_ErrorVolumeValue() {
        val input ="300-Q1"
        val (year, quarter) = DataParser.splitYearQuarter(input)
        assertEquals(0, year)
        assertEquals(0, quarter)

    }

    @Test
    fun splitYearQuarter_ErrorQuarterValue() {
        val input ="2008-Q5"
        val (year, quarter) = DataParser.splitYearQuarter(input)
        assertEquals(0, year)
        assertEquals(0, quarter)

    }

    @Test
    fun splitYearQuarter_ValidInput() {
        val input ="2008-Q1"
        val (year, quarter) = DataParser.splitYearQuarter(input)
        assertEquals(2008, year)
        assertEquals(1, quarter)
    }

    @Test
    fun transformModelToDbModel()  {
        val annualMobileDataList = mutableListOf<AnnualMobileData>()
        var annualMobileData1 = AnnualMobileData(2008)
        annualMobileData1.setQuarterVolume(1,0.1)
        annualMobileData1.setQuarterVolume(2,0.2)
        annualMobileData1.setQuarterVolume(3,0.3)
        annualMobileData1.setQuarterVolume(4,0.4)
        var annualMobileData2 = AnnualMobileData(2018)
        annualMobileData2.setQuarterVolume(1,0.8)
        annualMobileData2.setQuarterVolume(2,0.7)
        annualMobileData2.setQuarterVolume(3,0.6)
        annualMobileData2.setQuarterVolume(4,0.5)
        annualMobileDataList.add(annualMobileData1)
        annualMobileDataList.add(annualMobileData2)

        val outputList = DataParser.transformModelToDbModel(annualMobileDataList)

        assertEquals(annualMobileDataList.size, outputList.size)

        assertEquals(annualMobileData1.hasDecrease(), outputList[0].decrease)
        assertEquals(annualMobileData2.hasDecrease(), outputList[1].decrease)

        assertEquals(annualMobileData1.getAnnualDataVol(), outputList[0].totalVolume,0.0)
        assertEquals(annualMobileData2.getAnnualDataVol(), outputList[1].totalVolume,0.0)

    }

}