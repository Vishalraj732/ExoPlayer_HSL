package com.example.exoplayertask

import android.content.Context
import android.util.Log
import java.io.IOException

class Utils {
    companion object {
        fun getJsonFromAssets(context: Context, fileName: String?): String? {
            val jsonString: String = try {
                val input = context.assets.open(fileName!!)
                val size = input.available()
                val buffer = ByteArray(size)
                input.read(buffer)
                input.close()
                String(buffer, charset("UTF-8"))
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
            return jsonString
        }
    }
}