package com.scclzkj.base_core.utils

import com.wx.dex.high.level.application.SampleApplication
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.lang.StringBuilder

/**
 * 从assets 读取txt 文件内容
 */
object JsonTestDataManager {

    // 从assets 文件夹中获取文件并读取数据
    private fun getFromAssets(fileName: String): String {
        try {
            val inputReader = InputStreamReader(SampleApplication.application.assets.open(fileName))
            val bufReader = BufferedReader(inputReader)
            var line: String? = ""
            var result = StringBuilder()
            while (bufReader.readLine().also { line = it } != null) result.append(line)
            return result.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun getJsonByFile(strFile: String): String {
        return getFromAssets(strFile)
    }

    fun getBytesFromAssets(fileName: String): ByteArray {
        val inputStream = SampleApplication.application.assets.open(fileName)
        var len = 0
        val size = 1024
        var buf = ByteArray(size)
        val bos = ByteArrayOutputStream()
        while ((inputStream.read(buf, 0, size)).also { len = it } != -1) bos.write(buf, 0, len)
        buf = bos.toByteArray()
        return buf;
    }
}