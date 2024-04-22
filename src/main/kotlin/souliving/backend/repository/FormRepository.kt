package souliving.backend.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import souliving.backend.model.Form

@Repository
interface FormRepository : CoroutineCrudRepository<Form, Long> {
    suspend fun findByUserId(userId: Long): Form?
}
