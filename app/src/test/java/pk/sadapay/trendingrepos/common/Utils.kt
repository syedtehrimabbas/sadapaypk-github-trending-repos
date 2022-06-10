package pk.sadapay.trendingrepos.common

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

object Utils {

    @Throws(IOException::class)
    fun readFileFromTestResources(fileName: String): String {
        var inputStream: InputStream? = null
        try {
            inputStream = getInputStreamFromResource(fileName)
            val builder = StringBuilder()
            val reader = BufferedReader(InputStreamReader(inputStream))

            var str: String? = reader.readLine()
            while (str != null) {
                builder.append(str)
                str = reader.readLine()
            }
            return builder.toString()
        } finally {
            inputStream?.close()
        }
    }

    private fun getInputStreamFromResource(fileName: String) =
        javaClass.classLoader?.getResourceAsStream(fileName)

    fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
        val inputStream = getInputStreamFromResource(fileName)

        val source = inputStream?.let { inputStream.source().buffer() }
        source?.let {
            enqueue(
                MockResponse()
                    .setResponseCode(code)
                    .setBody(source.readString(StandardCharsets.UTF_8))
            )
        }
    }
}