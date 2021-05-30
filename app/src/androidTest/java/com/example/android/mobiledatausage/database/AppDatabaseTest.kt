package com.example.android.mobiledatausage.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.mobiledatausage.viewmodel.DataViewModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: DbDAO

    @Before
    fun setUp() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        // initialize the db and dao variable
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.dbDAO
    }

    @Test
    fun getAllRecords() = runBlocking {
        val records = mutableListOf<DbAnuualMobileData>()
        records.add(DbAnuualMobileData(2008,
            0.1,
            0.2,
            0.3,
            0.4,
            1.0,
            false
        ))

        records.add(DbAnuualMobileData(2009,
            0.5,
            0.6,
            0.7,
            0.8,
            26.0,
            false
        ))

        dao.insertAll(*records.toTypedArray())

        val dbRecords = dao.getAllRecords()

        assertEquals(records.size, dbRecords.size)

        assertEquals(records[0].year, dbRecords[0].year)

        for(x in 0..1) {
            assertEquals(records[x].year, dbRecords[x].year)
            assertEquals(records[x].q1, dbRecords[x].q1, 0.0)
            assertEquals(records[x].q2, dbRecords[x].q2, 0.0)
            assertEquals(records[x].q3, dbRecords[x].q3, 0.0)
            assertEquals(records[x].q4, dbRecords[x].q4, 0.0)
            assertEquals(records[x].totalVolume, dbRecords[x].totalVolume, 0.0)
            assertEquals(records[x].decrease, dbRecords[x].decrease)
        }
    }

    @After
    fun closeDb() {
        db.close()
    }
}