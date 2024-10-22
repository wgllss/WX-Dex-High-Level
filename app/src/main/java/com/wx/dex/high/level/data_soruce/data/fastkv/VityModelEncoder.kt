package com.wx.dex.high.level.data_soruce.data.fastkv

import com.wx.dex.high.level.data_soruce.data.city.CityP
import io.fastkv.interfaces.FastEncoder
import io.packable.PackDecoder
import io.packable.PackEncoder
//
//object VityModelEncoder:FastEncoder<List<CityP>> {
//    override fun tag(): String {
//        return "LongList"
//    }
//
//    override fun decode(bytes: ByteArray, offset: Int, length: Int): List<CityP> {
//        return ArrayList(PackDecoder.newInstance(bytes, offset, length).getLongList(0))
//
//    }
//
//    override fun encode(obj: List<CityP>): ByteArray {
//        return PackEncoder().putLongList(0, obj).bytes
//    }
//
//}