package souliving.backend.utils

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.zip.DataFormatException
import java.util.zip.Deflater
import java.util.zip.Inflater


object ImageUtils {
    private val BITE_SIZE: Int = 4 * 1024

    @Throws(IOException::class)
    fun compressImage(data: ByteArray): ByteArray {
        val deflater = Deflater()
        deflater.setLevel(Deflater.BEST_COMPRESSION)
        deflater.setInput(data)
        deflater.finish()

        val outputStream = ByteArrayOutputStream(data.size)
        val tmp = ByteArray(BITE_SIZE)

        while (!deflater.finished()) {
            val size = deflater.deflate(tmp)
            outputStream.write(tmp, 0, size)
        }

        outputStream.close()
        return outputStream.toByteArray()
    }

    @Throws(DataFormatException::class, IOException::class)
    fun decompressImage(data: ByteArray): ByteArray {
        val inflater = Inflater()
        inflater.setInput(data)
        val outputStream = ByteArrayOutputStream(data.size)
        val tmp = ByteArray(BITE_SIZE)

        while (!inflater.finished()) {
            val count = inflater.inflate(tmp)
            outputStream.write(tmp, 0, count)
        }

        outputStream.close()
        return outputStream.toByteArray()
    }
}
