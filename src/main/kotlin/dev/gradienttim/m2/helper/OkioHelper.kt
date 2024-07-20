package dev.gradienttim.m2.helper

import okio.Source
import okio.buffer
import okio.sink
import java.io.File

object OkioHelper {

    fun sourceToFile(source: Source, file: File) {
        val bufferedSource = source.buffer()
        val bufferedSink = file.sink().buffer()
        bufferedSink.writeAll(bufferedSource)
        bufferedSource.close()
        bufferedSink.close()
    }

}