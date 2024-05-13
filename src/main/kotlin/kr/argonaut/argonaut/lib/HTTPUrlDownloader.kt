package kr.argonaut.argonaut.lib

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

object HTTPUrlDownloader {
    fun downloadFile(url: URL): Array<Byte> {
        try {
            val connection = url.openConnection()as HttpURLConnection
            connection.requestMethod = "GET"

            connection.connect()
            checkConnectionSuccess(connection)

            val output = ByteArrayOutputStream()
            val input = connection.inputStream

            use(output, input) { outputStream, inputStream ->
                download(outputStream, inputStream)
            }
            return output.toByteArray().toTypedArray()
        } catch (e: IOException) {
            e.printStackTrace()
            throw e
        }
    }

    private fun checkConnectionSuccess(connection: HttpURLConnection) {
        if (connection.responseCode != HttpURLConnection.HTTP_OK) {
            throw IOException("Server returned HTTP response code: ${connection.responseCode} for URL: ${connection.url}. (message: '${connection.responseMessage}')")
        }
    }

    private fun download(outputStream: OutputStream, inputStream: InputStream) {
        val buffer = ByteArray(4096)
        var bytesRead: Int

        while (inputStream.read(buffer).also { bytesRead = it } != -1)
            outputStream.write(buffer, 0, bytesRead)

        outputStream.close()
        inputStream.close()
    }
}