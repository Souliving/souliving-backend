package souliving.backend.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.MediaType.IMAGE_PNG_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import souliving.backend.dto.ImageUserDto
import souliving.backend.logger.logResponse
import souliving.backend.service.ImageService


@RestController
@CrossOrigin
@RequestMapping("/api/v1/image")
class ImageController(
    @Autowired
    var imageService: ImageService
) {

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    suspend fun uploadImage(@RequestPart("image") image: FilePart?): Long {
        val uploadImageId: Long = imageService.uploadImage(image!!)
            ?: throw ResponseStatusException(HttpStatus.CONFLICT, "You should provide a valid file")
        logResponse("Upload image with name: $uploadImageId")
        return uploadImageId
    }

    @GetMapping("/{fileName}")
    suspend fun downloadImage(@PathVariable fileName: String?): ResponseEntity<*> {
        val imageData: ByteArray? = imageService.downloadImage(fileName!!)
        logResponse("Download image with name: $fileName")
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
            .body<ByteArray>(imageData)
    }

    @GetMapping("getImageById/{id}")
    suspend fun getImage(@PathVariable id: Long): ResponseEntity<*> {
        val imageData: ByteArray? = imageService.downloadImageById(id)
        logResponse("Download image with id: $id")
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
            .body<ByteArray>(imageData)
    }

    @GetMapping("getImageByFormId/{formId}")
    suspend fun getImageByFormId(@PathVariable formId: Long): ResponseEntity<*> {
        val imageData: ByteArray? = imageService.downloadImageByFormId(formId)
        logResponse("Download image by user id: $formId")
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
            .body<ByteArray>(imageData)
    }

    @GetMapping("getImageByUserId/{userId}")
    suspend fun getImageByUserId(@PathVariable userId: Long): ResponseEntity<*> {
        val imageData: ByteArray? = imageService.downloadImageByUserId(userId)
        logResponse("Download image by user id: $userId")
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
            .body<ByteArray>(imageData)
    }

    @PostMapping("uploadImageByUserId")
    suspend fun uploadImageByUserId(@RequestBody dto: ImageUserDto): ResponseEntity<*> {
        val result = imageService.uploadImageByUserId(dto.imageId, dto.userId)
        return if (result) {
            logResponse("Upload image by user id: ${dto.userId}")
            ResponseEntity.ok("Success upload image")
        } else {
            ResponseEntity.internalServerError().body("Problem with uploading")
        }
    }
}
