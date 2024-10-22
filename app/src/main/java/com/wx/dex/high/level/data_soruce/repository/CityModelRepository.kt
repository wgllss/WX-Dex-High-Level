package com.wx.dex.high.level.data_soruce.repository

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.scclzkj.base_core.utils.JsonTestDataManager
import com.wx.city.model.ICityModel
import com.wx.dex.high.level.application.SampleApplication
import com.wx.dex.high.level.code.classloader.WXClassLoader
import com.wx.dex.high.level.code.utils.DynamicManageUtils
import com.wx.dex.high.level.code.utils.DynamicManageUtils.getDlfn
import com.wx.dex.high.level.data_soruce.data.CityModelImpl
import io.fastkv.FastKV


class CityModelRepository private constructor() {

    private val context by lazy { SampleApplication.application }
    private val kv by lazy { FastKV.Builder(context, "fastKV").build() }

    companion object {
        val instance by lazy { CityModelRepository() }
    }


    fun saveJson() {
        val json = JsonTestDataManager.getJsonByFile("city/city_out.txt")
        kv.putString("json", json)
        log("存入json成功")
    }

    fun readJson() {
        val startTime = System.currentTimeMillis()
        val json = kv.getString("json")
        log("从fastKV 读取json:耗时:${(System.currentTimeMillis() - startTime)} ms ")
        val map = JSON.parseObject(json, object : TypeReference<Map<String, List<String>>>() {})

        val endTime = System.currentTimeMillis()
        map["北京"]?.let {
            log("到FastJson解析结束 耗时:${(endTime - startTime)} ms ${it[0]}")
        }
    }

    fun saveProtobuf() {
        val bytes = JsonTestDataManager.getBytesFromAssets("city/city_out_p")
        kv.putArray("byte_key", bytes)
        log("存入Protobuf成功")
    }

    fun readProtobuf() {
        val startTime = System.currentTimeMillis()
        val bytes = kv.getArray("byte_key")
        val model = com.wx.dex.high.level.data_soruce.data.city.protobuf.CityModel._CityModel.parseFrom(bytes)
        val endTime = System.currentTimeMillis()
        log("从FastKV 拿到byte 到解析出protobuf 耗时：${(endTime - startTime)} ${model.mapMap.get("北京")!!.valuesList.get(0)}")
    }


    fun savePluginDexFile() {
        DynamicManageUtils.getDxFile(context, "d_dex", getDlfn("city_model_lib_dex", 1000)).takeUnless { it.exists() }?.run {
            DynamicManageUtils.copyFileFromAssetsToSD(context, this, "city/city_model_lib_dex")
        }
    }

    fun loadPluginDexFile() {
        val startTime = System.currentTimeMillis()
        val cityModelImpl = WXClassLoader(DynamicManageUtils.getDxFile(context, "d_dex", getDlfn("city_model_lib_dex", 1000)).absolutePath, null, context.classLoader).getInterface(ICityModel::class.java, "com.wx.city.model.impl.PluginCityModelImpl")
        val endTime = System.currentTimeMillis()
        log(" 加载插件包到使用的 耗时：${(endTime - startTime)} ms ${cityModelImpl.cityMap().get("北京")!!.get(0)}")
    }


    fun load() {
        val startTime = System.currentTimeMillis()
        val cityModelImpl = CityModelImpl()
        val endTime = System.currentTimeMillis()
        log(" 加载包内使用的 耗时：${(endTime - startTime)} ms ${cityModelImpl.cityMap().get("北京")!!.get(0)}")
    }

    fun deletedexPlugin() {
        DynamicManageUtils.getDxFile(context, "d_dex", getDlfn("city_model_lib_dex", 1000)).takeIf { it.exists() }?.delete()
        log("插件删除成功")
    }

    fun strategyMode() {
        // 这里我就不用标准策略模式写法了，用到策略思想
        val startTime = System.currentTimeMillis()
        val file = DynamicManageUtils.getDxFile(context, "d_dex", getDlfn("city_model_lib_dex", 1000))
        val cityModelImpl = if (file.exists()) WXClassLoader(file.absolutePath, null, context.classLoader).getInterface(ICityModel::class.java, "com.wx.city.model.impl.PluginCityModelImpl")
        else CityModelImpl()
        val endTime = System.currentTimeMillis()
        log(" 策略模式使用的 耗时：${(endTime - startTime)} ms ${cityModelImpl.cityMap().get("北京")!!.get(0)}")
    }

    private fun log(str: String) {
        android.util.Log.e("CityModelRepository", str)
    }
}