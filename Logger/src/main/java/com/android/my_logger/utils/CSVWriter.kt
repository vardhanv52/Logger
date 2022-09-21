package com.android.my_logger.utils;

import android.util.Xml
import java.io.IOException;
import java.io.OutputStream
import java.io.PrintWriter
import java.io.Writer;

internal class CSVWriter {
    var writer: Writer? = null
    var printWriter: PrintWriter? = null
    var outputStream: OutputStream? = null

    constructor(writer: Writer) {
        this.writer = writer
        printWriter = PrintWriter(writer)
    }

    constructor(outputStream: OutputStream) {
        this.outputStream = outputStream
    }

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
            sb.append(nextLine[i]?.replace(",", ";") ?: "")
        }
        sb.append(lineEnd)
        LogUtil.log(sb.toString())
        if(printWriter != null)
            printWriter!!.write(sb.toString())
        else
            outputStream!!.write(sb.toString().toByteArray())
    }

    fun flush() {
        printWriter?.flush()
        outputStream?.flush()
    }

    fun close() {
        flush()
        printWriter?.close()
        outputStream?.close()
    }

}