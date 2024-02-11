package souliving.backend.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import souliving.backend.model.User

interface UserRepository : CoroutineCrudRepository<User, Long> {
    suspend fun findByName(name: String): User?
    suspend fun findByEmail(email: String): User?
}
