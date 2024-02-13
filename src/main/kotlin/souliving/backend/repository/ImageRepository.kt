package souliving.backend.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import souliving.backend.model.image.Image

interface ImageRepository : CoroutineCrudRepository<Image, Long> {
    suspend fun findByName(name: String): Image
}
