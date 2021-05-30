package com.example.android.mobiledatausage.repository

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.mobiledatausage.database.AppDatabase
import com.example.android.mobiledatausage.database.DbDAO
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DataRepositoryAndroidTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: AppDatabase
    private lateinit var dao: DbDAO
    private lateinit var repo: DataRepository

    @Before
    fun setUp() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        // initialize the db and dao variable
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.dbDAO
        repo = DataRepository(db)
    }

    @Test
    fun getData() {
        runBlocking {

            repo.getData()

            val dbRecords = dao.getAllRecords()
            val sizeExpected = dbRecords.size
            val sizeActual = repo.records.value?.size

            assertEquals(sizeExpected, sizeActual)
        }
    }

    @After
    fun closeDb() {
        db.close()
    }

}