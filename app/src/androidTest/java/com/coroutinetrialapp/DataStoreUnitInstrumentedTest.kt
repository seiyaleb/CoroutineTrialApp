package com.coroutinetrialapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.coroutinetrialapp.datastore.LaunchDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.*
import org.junit.After

import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.File

//DataStoreのデータを確認する単体テスト
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class DataStoreUnitInstrumentedTest {

    private val TEST_DATASTORE_NAME = "preferences"
    private val dispatcher = UnconfinedTestDispatcher()
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val scope = TestScope(dispatcher + Job())
    private val dataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = scope,
            produceFile = { context.preferencesDataStoreFile(TEST_DATASTORE_NAME) }
        )
    private val launchDataStore = LaunchDataStore(dataStore)

    @Before
    fun setup() {
        //データを毎回新規にするためにファイル全削除
        context.run {
            File(filesDir, "datastore").deleteRecursively()
        }
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        scope.cancel()
    }

    @Test
    fun validateIsTrue() {
        scope.runTest {
            assertEquals(false,launchDataStore.preferenceFlow.firstOrNull())
            //trueに変更
            launchDataStore.saveLaunch()
            assertEquals(true,launchDataStore.preferenceFlow.firstOrNull())
        }
    }
}