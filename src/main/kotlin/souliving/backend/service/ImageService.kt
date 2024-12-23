package souliving.backend.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.commons.lang3.exception.ContextedRuntimeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.codec.multipart.FilePart
import org.springframework.r2dbc.core.*
import org.springframework.stereotype.Service
import souliving.backend.model.image.Image
import souliving.backend.repository.FormRepository
import souliving.backend.repository.ImageRepository
import souliving.backend.utils.ImageUtils
import java.io.IOException
import java.util.*
import java.util.zip.DataFormatException


@Service
class ImageService(
    @Autowired
    val imageRepository: ImageRepository,
    @Autowired
    val formRepository: FormRepository,
    @Autowired
    private val databaseClient: DatabaseClient,
) {
    @Throws(IOException::class)
    suspend fun uploadImage(imageFile: FilePart): Long? {
        val imageToSave = Image(
            name = imageFile.filename(), type = imageFile.headers().contentType!!.type,
            container = ImageUtils.compressImage(
                withContext(Dispatchers.IO) {
                    DataBufferUtils.join(imageFile.content())
                        .map { dataBuffer -> dataBuffer.toByteBuffer().array() }.block()
                }!!
            )
        )

        val image = imageRepository.save(imageToSave)
        return image.id
    }

    suspend fun downloadImage(imageName: String?): ByteArray? {
        val dbImage: Image = imageRepository.findByName(imageName!!)
        try {
            return ImageUtils.decompressImage(dbImage.container)
        } catch (exception: DataFormatException) {
            throw ContextedRuntimeException("Error downloading an image", exception)
                .addContextValue("Image ID", dbImage.id)
                .addContextValue("Image name", imageName)
        } catch (exception: IOException) {
            throw ContextedRuntimeException("Error downloading an image", exception)
                .addContextValue("Image ID", dbImage.id)
                .addContextValue("Image name", imageName)
        }
    }

    suspend fun downloadImageById(id: Long): ByteArray? {
        val dbImage: Image = imageRepository.findById(id)
        return decompressImage(dbImage)
    }

    suspend fun downloadImageByFormId(formId: Long): ByteArray? {
        val form = formRepository.findById(formId)
        val dbImage: Image = imageRepository.findById(form?.photoId!!)
        return decompressImage(dbImage)
    }

    suspend fun downloadImageByUserId(userId: Long): ByteArray? {
        val result = databaseClient.sql("select up.photo_id from user_photos_ids up where up.user_id = $userId").fetch()
            .awaitSingle()
        var photoId: Long = 0
        result.forEach { (t, u) ->
            if (t == "photo_id")
                photoId = u as Long
        }
        return downloadImageById(photoId)
    }

    suspend fun uploadImageByUserId(imageId: Long, userId: Long): Boolean {
        val result = databaseClient.sql("INSERT INTO user_photos_ids(user_id, photo_id) VALUES (:user_id, :photo_id)")
            .bind("user_id", userId).bind("photo_id", imageId).fetch().awaitRowsUpdated()
        return result != 0L
    }


    fun decompressImage(dbImage: Image): ByteArray? {
        try {
            return ImageUtils.decompressImage(dbImage.container)
        } catch (exception: DataFormatException) {
            throw ContextedRuntimeException("Error downloading an image", exception)
                .addContextValue("Image ID", dbImage.id)
                .addContextValue("Image name", dbImage.name)
        } catch (exception: IOException) {
            throw ContextedRuntimeException("Error downloading an image", exception)
                .addContextValue("Image ID", dbImage.id)
                .addContextValue("Image name", dbImage.name)
        }
    }
}
