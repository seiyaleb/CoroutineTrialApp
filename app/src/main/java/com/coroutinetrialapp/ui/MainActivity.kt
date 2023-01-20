package com.coroutinetrialapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.coroutinetrialapp.R
import com.coroutinetrialapp.datastore.LaunchDataStore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var launchDataStore:LaunchDataStore
    private lateinit var tvWindSpeed:TextView
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //初回起動時のみダイアログ表示
        displayDialogOnFirst(launchDataStore)

        tvWindSpeed = findViewById(R.id.tv_windspeed)
        findViewById<Button>(R.id.btn_get).setOnClickListener{
            //データ反映
            viewModel.fetchWindSpeed()
        }
        findViewById<Button>(R.id.btn_reset).setOnClickListener{
            tvWindSpeed.text = ""
        }

        viewModel.windspeed.observe(this, Observer {
            tvWindSpeed.text = it
        })
    }

    private fun displayDialogOnFirst(_launchDataStore:LaunchDataStore) {
        lifecycleScope.launch {
            _launchDataStore.preferenceFlow.collect {
                //初回起動時にダイアログを表示
                if(!it) {
                    val strList = arrayOf("今後表示しない")
                    val checkedItems = booleanArrayOf(false)
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle(R.string.dialog_msg)
                        .setMultiChoiceItems(strList, checkedItems) { _, _, isChecked ->
                            //チェック入った場合、Preferencesをtrueへ
                            if (isChecked) {
                                launch {
                                    _launchDataStore.saveLaunch()
                                }
                            }
                        }
                        .setPositiveButton(R.string.dialog_close, null)
                        .show()
                }
            }
        }
    }
}
