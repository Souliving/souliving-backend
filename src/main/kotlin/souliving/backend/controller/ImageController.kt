package souliving.backend.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.MediaType.IMAGE_PNG_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*
import souliving.backend.service.ImageService


@RestController
@CrossOrigin
@RequestMapping("/api/v1/image")
class ImageController(
    @Autowired
    var imageService: ImageService
) {

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    suspend fun uploadImage(@RequestPart("image") image: FilePart?): ResponseEntity<*> {
        val uploadImage: String = image?.let { imageService.uploadImage(image) }
            ?: return ResponseEntity.status(HttpStatus.CONFLICT).body("You should provide a valid file")
        return ResponseEntity.status(HttpStatus.OK).body(uploadImage)
    }

    @GetMapping("/{fileName}")
    suspend fun downloadImage(@PathVariable fileName: String?): ResponseEntity<*> {
        val imageData: ByteArray? = imageService.downloadImage(fileName!!)
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
            .body<ByteArray>(imageData)
    }
}
