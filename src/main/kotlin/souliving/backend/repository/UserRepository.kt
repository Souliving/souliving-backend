package souliving.backend.repository

import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import souliving.backend.model.User

interface UserRepository : CoroutineCrudRepository<User, Long> {
    fun findByName(name: String): Flow<User>
}
