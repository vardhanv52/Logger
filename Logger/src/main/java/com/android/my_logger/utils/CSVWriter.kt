package com.android.my_logger.utils;

import java.io.IOException;
import java.io.PrintWriter
import java.io.Writer;

internal class CSVWriter(writer: Writer) {

    private var printWriter = PrintWriter(writer)
    private var separator = ','
    private var lineEnd = '\n'

    fun write(data: ArrayList<ArrayList<String?>>) {
        data.forEach {
            writeNext(it)
        }
    }

    private fun writeNext(nextLine: ArrayList<String?>) {
        val sb = StringBuffer()
        for (i in 0 until nextLine.size) {
            if (i != 0)
                sb.append(separator)
            sb.append(nextLine[i])
        }
        sb.append(lineEnd)
        printWriter.write(sb.toString())
    }

    fun flush() {
        printWriter.flush()
    }

    fun close() {
        printWriter.flush()
        printWriter.close()
    }

}