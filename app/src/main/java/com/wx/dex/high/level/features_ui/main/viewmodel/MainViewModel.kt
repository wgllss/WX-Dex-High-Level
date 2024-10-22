package com.wx.dex.high.level.features_ui.main.viewmodel

import androidx.lifecycle.ViewModel
import com.wx.dex.high.level.data_soruce.repository.CityModelRepository

class MainViewModel : ViewModel() {

    private val repository by lazy { CityModelRepository.instance }


    fun saveJson() {
        repository.saveJson()
    }

    fun readJson() {
        repository.readJson()
    }

    fun saveProtobuf() {
        repository.saveProtobuf()
    }

    fun readProtobuf() {
        repository.readProtobuf()
    }


    fun saveDexFile() {
        repository.savePluginDexFile()
    }

    fun loadDexFile() {
        repository.loadPluginDexFile()
    }

    fun load() {
        repository.load()
    }

    fun deletedexPlugin() {
        repository.deletedexPlugin()
    }

    fun strategyMode() {
        repository.strategyMode()
    }
}