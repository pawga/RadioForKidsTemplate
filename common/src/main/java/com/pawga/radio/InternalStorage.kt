package com.pawga.radio

import android.content.Context
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * Created by sivannikov
 */

class InternalStorage(private val context: Context) {
    @Throws(Exception::class)
    fun writeObject(key: String, `object`: Any?) {
        val fos = context.openFileOutput(key, Context.MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)
        oos.writeObject(`object`)
        oos.close()
        fos.close()
    }

    @Throws(Exception::class)
    fun readObject(key: String): Any? {
        val fis = context.openFileInput(key)
        val ois = ObjectInputStream(fis)
        val `object` = ois.readObject()
        ois.close()
        return `object`
    }
}